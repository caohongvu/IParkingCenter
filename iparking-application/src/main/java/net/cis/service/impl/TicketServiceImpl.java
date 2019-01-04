package net.cis.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.cis.common.util.constant.URLConstants;
import net.cis.dto.ParkingDto;
import net.cis.dto.TicketDto;
import net.cis.dto.TicketPriceDto;
import net.cis.jpa.criteria.TicketCriteria;
import net.cis.jpa.entity.TicketEntity;
import net.cis.repository.TicketRepository;
import net.cis.service.TicketService;
import net.cis.service.cache.MonthlyTicketCache;
import net.cis.service.cache.ParkingPlaceCache;
import net.cis.utils.RestfulUtil;

/**
 * Created by Vincent on 02/10/2018
 */
@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private ParkingPlaceCache parkingPlaceCache;

	@Autowired
	private MonthlyTicketCache monthlyTicketCache;

	ModelMapper mapper;

	@Override
	public TicketDto save(TicketDto ticketDto) {
		ModelMapper mapper = new ModelMapper();
		TicketEntity ticketEntity = new TicketEntity();
		mapper.map(ticketDto, ticketEntity);
		mapper.map(ticketRepository.save(ticketEntity), ticketDto);
		return ticketDto;
	}

	@Override
	public TicketDto findById(long id) {
		ModelMapper mapper = new ModelMapper();
		TicketEntity entity = ticketRepository.findOne(id);
		if (entity == null) {
			return null;
		}
		TicketDto ticketDto = new TicketDto();
		mapper.map(entity, ticketDto);
		ParkingDto parkingDto = parkingPlaceCache.get(String.valueOf(ticketDto.getParkingPlace()));
		if (parkingDto != null) {
			ticketDto.setCppCode(parkingDto.getParkingCode());
			ticketDto.setCppAddress(parkingDto.getAddress());
		}
		return ticketDto;
	}

	@Override
	public List<TicketDto> findAll(TicketCriteria ticketCriteria, Pageable pageable) {

		List<TicketEntity> ticketEntities = null;
		Boolean inSession = null;
		if (ticketCriteria.getInSession() != null) {
			inSession = ticketCriteria.getInSession() == 1;
		}
		ticketEntities = ticketRepository.findAll(ticketCriteria.getCppId(), ticketCriteria.getCustomer(), inSession,
				ticketCriteria.getFromDate(), ticketCriteria.getToDate(), ticketCriteria.getStatus(), pageable);

		List<TicketDto> ticketDtos = this.map(ticketEntities);
		return ticketDtos;
	}

	private List<TicketDto> map(List<TicketEntity> source) {

		ArrayList<TicketDto> rtn = new ArrayList<>();
		source.stream().map((entity) -> {
			TicketDto dto = new TicketDto();
			mapper.map(entity, dto);
			ParkingDto parkingDto = parkingPlaceCache.get(String.valueOf(entity.getParkingPlace()));
			if (parkingDto != null) {
				dto.setCppCode(parkingDto.getParkingCode());
				dto.setCppAddress(parkingDto.getAddress());
			}
			return dto;
		}).forEachOrdered((dto) -> {
			rtn.add(dto);
		});
		return rtn;
	}

	@PostConstruct
	public void initialize() {
		mapper = new ModelMapper();
		List<TicketEntity> monthlyTicketsInSession = ticketRepository.findByMonthlyTicketInSession();
		for (TicketEntity entity : monthlyTicketsInSession) {
			monthlyTicketCache.put(entity.getMonthlyTicketId(), entity.getId());
		}

	}

	@Override
	public void delete(TicketDto ticketDto) {
		if (ticketDto != null && ticketDto.getId() > 0) {
			ticketRepository.delete(ticketDto.getId());
		}
	}

	@Override
	public TicketPriceDto getPrice(String ticketId, int min) {
		String finalURL = URLConstants.TICKET_GET_PRICE;
		finalURL = finalURL.replace("{ticketId}", ticketId);
		finalURL = finalURL.replace("{min}", String.valueOf(min));

		String dataResult = RestfulUtil.getWithOutAccessToke(finalURL, null);
		TicketPriceDto ticketPrice = new TicketPriceDto();
		try {
			parseJSonToTicketPriceObject(dataResult, ticketPrice);
		} catch (JSONException e) {
			e.printStackTrace();
			ticketPrice = null;
		}

		return ticketPrice;
	}
	
	private void parseJSonToTicketPriceObject(String dataResult, TicketPriceDto ticketPrice) throws JSONException {
		JSONObject ticketJSon = new JSONObject(dataResult);
		JSONObject ticketDataJSon = ticketJSon.getJSONObject("Data");
		if(ticketDataJSon.has("Apply_from_time")) {
			ticketPrice.setApplyFromTime(ticketDataJSon.getString("Apply_from_time"));
		}
		if(ticketDataJSon.has("Apply_to_time")) {
			ticketPrice.setApplyToTime(ticketDataJSon.getString("Apply_to_time"));
		}
		if(ticketDataJSon.has("Cus_id")) {
			ticketPrice.setCusId(ticketDataJSon.getLong("Cus_id"));
		}
		if(ticketDataJSon.has("Cus_token")) {
			ticketPrice.setCusToken(ticketDataJSon.getLong("Cus_token"));
		}
		if(ticketDataJSon.has("Order_id")) {
			ticketPrice.setOrderId(ticketDataJSon.getString("Order_id"));
		}
		if(ticketDataJSon.has("Payment_amount")) {
			ticketPrice.setPaymentAmount(ticketDataJSon.getDouble("Payment_amount"));
		}
		if(ticketDataJSon.has("Payment_request_startat")) {
			ticketPrice.setPaymentRequestAt(ticketDataJSon.getString("Payment_request_startat"));
		}
		if(ticketDataJSon.has("Payment_request_endat")) {
			ticketPrice.setPaymentRequestEndAt(ticketDataJSon.getString("Payment_request_endat"));
		}
		if(ticketDataJSon.has("Payment_status")) {
			ticketPrice.setPaymentStatus(ticketDataJSon.getInt("Payment_status"));
		}
		if(ticketDataJSon.has("Ticket_id")) {
			ticketPrice.setTicketId(ticketDataJSon.getLong("Ticket_id"));
		}
		
	}

}

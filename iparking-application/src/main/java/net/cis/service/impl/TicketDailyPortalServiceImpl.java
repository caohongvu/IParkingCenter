package net.cis.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.cis.constants.ResponseErrorCodeConstants;
import net.cis.dto.DailyTicketPaymentDto;
import net.cis.dto.ErrorDto;
import net.cis.dto.ParkingDto;
import net.cis.dto.ResponseApi;
import net.cis.dto.TicketDailyPortalDto;
import net.cis.dto.TicketTransactionDto;
import net.cis.jpa.criteria.TicketDailyCriteria;
import net.cis.jpa.entity.DailyTicketPaymentEntity;
import net.cis.jpa.entity.TicketDailyPortalEntity;
import net.cis.jpa.entity.TicketTransactionEntity;
import net.cis.repository.TicketDailyPortalRepository;
import net.cis.service.TicketDailyPortalService;

@Service
public class TicketDailyPortalServiceImpl implements TicketDailyPortalService{
	
	@Autowired
	TicketDailyPortalRepository ticketDailyPortalRepository;
	
	ModelMapper mapper;
	private List<TicketDailyPortalDto> map(List<TicketDailyPortalEntity> source) {

		ArrayList<TicketDailyPortalDto> rtn = new ArrayList<TicketDailyPortalDto>();
		source.stream().map((entity) -> {
			TicketDailyPortalDto dto = new TicketDailyPortalDto();
			mapper.map(entity, dto);
			
			return dto;
		}).forEachOrdered((dto) -> {
			rtn.add(dto);
		});
		return rtn;
	}
	
	@PostConstruct
	public void initialize() {
		mapper = new ModelMapper();
	}

	@Override
	public ResponseApi getAllTicketDailyPortal(TicketDailyCriteria ticketCriteria, Pageable pageable) {
		// TODO Auto-generated method stub
		List<TicketDailyPortalEntity> ticketPortalEntity = null;
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		
		try {
			ticketPortalEntity = ticketDailyPortalRepository.getAllTicketDailyPortal(ticketCriteria.getCppCode(), ticketCriteria.getNumberplate(), ticketCriteria.getPhone2(), ticketCriteria.getStart_time(), ticketCriteria.getTo_time(), pageable);
			if (ticketPortalEntity == null || ticketPortalEntity.size() == 0) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
				errorDto.setMessage("");
				responseApi.setError(errorDto);
				return responseApi;
			}
			List<TicketDailyPortalDto> ticketDtos = this.map(ticketPortalEntity);
			
			errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
			errorDto.setMessage("");
			responseApi.setError(errorDto);
			responseApi.setData(ticketDtos);
			return responseApi;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			
		}
		return responseApi;
	}

}

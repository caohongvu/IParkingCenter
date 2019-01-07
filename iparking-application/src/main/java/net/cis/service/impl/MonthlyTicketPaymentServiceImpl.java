package net.cis.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import net.cis.constants.ResponseErrorCodeConstants;
import net.cis.dto.DailyTicketPaymentDto;
import net.cis.dto.DailyTicketPaymentEndPointDto;
import net.cis.dto.ErrorDto;
import net.cis.dto.MonthlyTicketPaymentDto;
import net.cis.dto.MonthlyTicketPaymentEndPointDto;
import net.cis.dto.ParkingDto;
import net.cis.dto.ResponseApi;
import net.cis.jpa.criteria.MonthlyTicketPaymentCriteria;
import net.cis.jpa.entity.DailyTicketPaymentEntity;
import net.cis.jpa.entity.DailyTicketPaymentFooterEntity;
import net.cis.jpa.entity.MonthlyTicketPaymentEntity;
import net.cis.jpa.entity.MonthlyTicketPaymentFooterEntity;
import net.cis.repository.MonthlyTicketPaymentFooterRepository;
import net.cis.repository.MonthlyTicketPaymentRepository;
import net.cis.service.MonthlyTicketPaymentService;

public class MonthlyTicketPaymentServiceImpl implements MonthlyTicketPaymentService {
	
	@Autowired
	MonthlyTicketPaymentRepository monthlyTicketPaymentRepository;
	
	@Autowired
	MonthlyTicketPaymentFooterRepository monthlyTicketPaymentFooterRepository;
	
	ModelMapper mapper;

	private List<MonthlyTicketPaymentDto> map(List<MonthlyTicketPaymentEntity> source) {

		ArrayList<MonthlyTicketPaymentDto> rtn = new ArrayList<>();
		source.stream().map((entity) -> {
			MonthlyTicketPaymentDto dto = new MonthlyTicketPaymentDto();
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
	public ResponseApi findAll(MonthlyTicketPaymentCriteria monthlyTicketPaymentCriteria, Pageable pageable) {
		// TODO Auto-generated method stub
		MonthlyTicketPaymentEndPointDto ticketEndPointDtos = new MonthlyTicketPaymentEndPointDto();
		List<MonthlyTicketPaymentEntity> ticketEntities = null;
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		try {
			String[] times = new String[2];
			int month = 0, year = 0;
			if ( monthlyTicketPaymentCriteria.getPeriodPayment() != null) {
				times = monthlyTicketPaymentCriteria.getPeriodPayment().split("/");
				month = Integer.parseInt(times[0]);
				year =  Integer.parseInt(times[1]);
			}
			ticketEntities = monthlyTicketPaymentRepository.findAll(monthlyTicketPaymentCriteria.getTransId(), monthlyTicketPaymentCriteria.getCppCode(),
					monthlyTicketPaymentCriteria.getContract_no(), monthlyTicketPaymentCriteria.getContract_code(), monthlyTicketPaymentCriteria.getNumberplate(),
					monthlyTicketPaymentCriteria.getPhone(), monthlyTicketPaymentCriteria.getCardNumber(), monthlyTicketPaymentCriteria.getStart_time(),
					monthlyTicketPaymentCriteria.getEnd_time(), month, year, pageable);

			if (ticketEntities == null || ticketEntities.size() == 0) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
				errorDto.setMessage("");
				responseApi.setError(errorDto);
				return responseApi;
			}

			List<MonthlyTicketPaymentDto> ticketDtos = this.map(ticketEntities);
			MonthlyTicketPaymentFooterEntity ticketFooterEntity = null;
			try {

				ticketFooterEntity =  monthlyTicketPaymentFooterRepository.findAll(monthlyTicketPaymentCriteria.getTransId(), monthlyTicketPaymentCriteria.getCppCode(),
						monthlyTicketPaymentCriteria.getContract_no(), monthlyTicketPaymentCriteria.getContract_code(), monthlyTicketPaymentCriteria.getNumberplate(),
						monthlyTicketPaymentCriteria.getPhone(), monthlyTicketPaymentCriteria.getCardNumber(), monthlyTicketPaymentCriteria.getStart_time(),
						monthlyTicketPaymentCriteria.getEnd_time(), month, year);

				mapper.map(ticketFooterEntity, ticketEndPointDtos);
				ticketEndPointDtos.setMonthlyTicketPayment(ticketDtos);
				responseApi.setData(ticketEndPointDtos);
				errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
				errorDto.setMessage("");
				responseApi.setError(errorDto);
				return responseApi;
			} catch (Exception e) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusInternalServerError);
				errorDto.setMessage(ResponseErrorCodeConstants.DBAccessErr);
				responseApi.setError(errorDto);
				return responseApi;
			}

		} catch (Exception e) {
			errorDto.setCode(ResponseErrorCodeConstants.StatusInternalServerError);
			errorDto.setMessage(ResponseErrorCodeConstants.DBAccessErr);
			responseApi.setError(errorDto);
			return responseApi;
		}
	}

}

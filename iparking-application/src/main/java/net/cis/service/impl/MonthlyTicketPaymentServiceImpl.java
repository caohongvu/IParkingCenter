package net.cis.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.cis.constants.ResponseErrorCodeConstants;
import net.cis.dto.ErrorDto;
import net.cis.dto.MonthlyTicketPaymentDto;
import net.cis.dto.MonthlyTicketPaymentEndPointDto;
import net.cis.dto.ResponseApi;
import net.cis.jpa.criteria.MonthlyTicketPaymentCriteria;
import net.cis.jpa.entity.MonthlyTicketPaymentEntity;
import net.cis.repository.MonthlyTicketPaymentRepository;
import net.cis.service.MonthlyTicketPaymentService;

@Service
public class MonthlyTicketPaymentServiceImpl implements MonthlyTicketPaymentService {

	@Autowired
	MonthlyTicketPaymentRepository monthlyTicketPaymentRepository;



	ModelMapper mapper;

	private MonthlyTicketPaymentEndPointDto mapEndpoint(List<MonthlyTicketPaymentEntity> source) {
		MonthlyTicketPaymentEndPointDto ticketEndPointDtos = new MonthlyTicketPaymentEndPointDto();
		ArrayList<MonthlyTicketPaymentDto> rtn = new ArrayList<>();
		int total_row = source.size();
		HashSet<String> Cpp_code = new HashSet<String>();
		HashSet<String> ContractNo = new HashSet<String>();
		HashSet<String> ContracCode = new HashSet<String>();
		HashSet<Long> Phone = new HashSet<Long>();
		double totalPayment_amount = 0;
		for (MonthlyTicketPaymentEntity entity : source) {
			totalPayment_amount += entity.getPayment_amount();
			Cpp_code.add(entity.getParking_place());
			ContractNo.add(entity.getContract_no());
			ContracCode.add(entity.getContract_code());
			Phone.add(entity.getPhone());
			MonthlyTicketPaymentDto dto = new MonthlyTicketPaymentDto();
			mapper.map(entity, dto);
			
			SimpleDateFormat formatTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			try {
			Date applyFr = formatTime.parse(dto.getApply_from_time());
			Date applyTo = formatTime.parse(dto.getApply_to_time());

			String peroid_payment = formatTime.format(applyFr)+"-"+formatTime.format(applyTo);
			dto.setPeriodPayment(peroid_payment);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			rtn.add(dto);
		}

		ticketEndPointDtos.setTotal_row(total_row);
		ticketEndPointDtos.setCountCpp_code(Cpp_code.size());
		ticketEndPointDtos.setCountContractNo(ContractNo.size());
		ticketEndPointDtos.setCountContracCode(ContracCode.size());
		ticketEndPointDtos.setCountPhone(Phone.size());
		ticketEndPointDtos.setTotalPayment_amount(totalPayment_amount);
		ticketEndPointDtos.setMonthlyTicketPayment(rtn);

		return ticketEndPointDtos;
	}

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
			if (monthlyTicketPaymentCriteria.getPeriodPayment() != null) {
				times = monthlyTicketPaymentCriteria.getPeriodPayment().split("/");
				month = Integer.parseInt(times[0]);
				year = Integer.parseInt(times[1]);
			}
			ticketEntities = monthlyTicketPaymentRepository.findAll(monthlyTicketPaymentCriteria.getTransId(),
					monthlyTicketPaymentCriteria.getCppCode(), monthlyTicketPaymentCriteria.getContract_no(),
					monthlyTicketPaymentCriteria.getContract_code(), monthlyTicketPaymentCriteria.getNumberplate(),
					monthlyTicketPaymentCriteria.getPhone(), monthlyTicketPaymentCriteria.getCardNumber(),
					monthlyTicketPaymentCriteria.getStart_time(), monthlyTicketPaymentCriteria.getEnd_time(), month,
					year, pageable);

			if (ticketEntities == null || ticketEntities.size() == 0) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
				errorDto.setMessage("");
				responseApi.setError(errorDto);
				return responseApi;
			}

			ticketEndPointDtos = this.mapEndpoint(ticketEntities);
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
	}

}

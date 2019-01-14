package net.cis.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.cis.constants.ResponseErrorCodeConstants;
import net.cis.dto.DailyTicketPaymentDto;
import net.cis.dto.DailyTicketPaymentEndPointDto;
import net.cis.dto.DailyTicketRevenueDto;
import net.cis.dto.ErrorDto;
import net.cis.dto.ParkingDto;
import net.cis.dto.ResponseApi;
import net.cis.jpa.criteria.DailyTicketPaymentCriteria;
import net.cis.jpa.entity.DailyTicketPaymentEntity;
import net.cis.jpa.entity.DailyTicketPaymentFooterEntity;
import net.cis.repository.DailyTicketPaymentFooterRepository;
import net.cis.repository.DailyTicketPaymentRepository;
import net.cis.service.DailyTicketPaymentService;
import net.cis.service.cache.ParkingPlaceCache;

@Service
public class DailyTicketPaymentServiceImpl implements DailyTicketPaymentService {

	@Autowired
	DailyTicketPaymentRepository ticketRepository;

	@Autowired
	DailyTicketPaymentFooterRepository ticketFooterRepository;

	@Autowired
	private ParkingPlaceCache parkingPlaceCache;

	ModelMapper mapper;

	private List<DailyTicketPaymentDto> map(List<DailyTicketPaymentEntity> source) {

		ArrayList<DailyTicketPaymentDto> rtn = new ArrayList<>();
		source.stream().map((entity) -> {
			DailyTicketPaymentDto dto = new DailyTicketPaymentDto();
			mapper.map(entity, dto);
			ParkingDto parkingDto = parkingPlaceCache.get(String.valueOf(entity.getParking_code()));
			if (parkingDto != null) {
				dto.setCppCode(parkingDto.getParkingCode());
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
	}

	@Override
	public ResponseApi findAllFooter(DailyTicketPaymentCriteria ticketCriteria, Pageable pageable) {
		// TODO Auto-generated method stub
		DailyTicketPaymentEndPointDto ticketEndPointDtos = new DailyTicketPaymentEndPointDto();
		List<DailyTicketPaymentEntity> ticketEntities = null;
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		try {
			ticketEntities = ticketRepository.findAll(ticketCriteria.getOrderID(), ticketCriteria.getTransId(),
					ticketCriteria.getCppCode(), ticketCriteria.getNumberplate(), ticketCriteria.getPhone(),
					ticketCriteria.getStart_time(), ticketCriteria.getEnd_time(), ticketCriteria.getCardNumber(),
					ticketCriteria.getTransType(), pageable);

			if (ticketEntities == null || ticketEntities.size() == 0) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
				errorDto.setMessage("");
				responseApi.setError(errorDto);
				return responseApi;
			}

			List<DailyTicketPaymentDto> ticketDtos = this.map(ticketEntities);
			DailyTicketPaymentFooterEntity ticketFooterEntity = null;
			try {

				ticketFooterEntity = ticketFooterRepository.findAllFooter(ticketCriteria.getOrderID(),
						ticketCriteria.getTransId(), ticketCriteria.getCppCode(), ticketCriteria.getNumberplate(),
						ticketCriteria.getPhone(), ticketCriteria.getStart_time(), ticketCriteria.getEnd_time(),
						ticketCriteria.getCardNumber(), ticketCriteria.getTransType());

				mapper.map(ticketFooterEntity, ticketEndPointDtos);
				ticketEndPointDtos.setDailyTicketPayment(ticketDtos);
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

	@Override
	public ResponseApi getRevenueGroupByParkingCode(DailyTicketPaymentCriteria ticketCriteria) {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		List<DailyTicketRevenueDto> result = ticketRepository.getRevenueByParkingCode(ticketCriteria.getCppCode(),
				ticketCriteria.getStart_time(), ticketCriteria.getEnd_time());

		responseApi.setData(result);
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		errorDto.setMessage("");
		responseApi.setError(errorDto);
		return responseApi;
	}

	@Override
	public ResponseApi getRevenueGroupByCompanyCode(DailyTicketPaymentCriteria ticketCriteria) {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		List<DailyTicketRevenueDto> result = ticketRepository.getRevenueByCompanyCode(ticketCriteria.getCppCode(),
				ticketCriteria.getStart_time(), ticketCriteria.getEnd_time());

		responseApi.setData(result);
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		errorDto.setMessage("");
		responseApi.setError(errorDto);
		return responseApi;
	}

	@Autowired
	private EntityManager entityManager;

	@Override
	public ResponseApi getRevenueGroupByParkingCodeSP(DailyTicketPaymentCriteria ticketCriteria) {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		StoredProcedureQuery storedProcedureQuery = entityManager
				.createStoredProcedureQuery("daily_ticket_payment_group_by_parking_code");
		storedProcedureQuery.registerStoredProcedureParameter("cpp_code", String.class, ParameterMode.IN);
		storedProcedureQuery.registerStoredProcedureParameter("from_time", String.class, ParameterMode.IN);
		storedProcedureQuery.registerStoredProcedureParameter("to_time", String.class, ParameterMode.IN);
		storedProcedureQuery.setParameter("cpp_code", ticketCriteria.getCppCode());
		storedProcedureQuery.setParameter("from_time", ticketCriteria.getStart_time());
		storedProcedureQuery.setParameter("to_time", ticketCriteria.getEnd_time());
		storedProcedureQuery.execute();
		List<DailyTicketRevenueDto> result = new ArrayList<>();
		List<Object[]> lst = storedProcedureQuery.getResultList();
		for (Object[] value : lst) {
			DailyTicketRevenueDto obDailyTicketRevenueDto = new DailyTicketRevenueDto();
			if (value[0] != null)
				obDailyTicketRevenueDto.setCode(value[0].toString());

			if (value[1] != null)
				obDailyTicketRevenueDto.setRevenue((double) value[1]);

			result.add(obDailyTicketRevenueDto);
		}
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		responseApi.setData(result);
		responseApi.setError(errorDto);
		return responseApi;
	}

	@Override
	public ResponseApi getRevenueGroupByCompanyCodeSP(DailyTicketPaymentCriteria ticketCriteria) {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		StoredProcedureQuery storedProcedureQuery = entityManager
				.createStoredProcedureQuery("daily_ticket_payment_group_by_company");
		storedProcedureQuery.registerStoredProcedureParameter("cpp_code", String.class, ParameterMode.IN);
		storedProcedureQuery.registerStoredProcedureParameter("from_time", String.class, ParameterMode.IN);
		storedProcedureQuery.registerStoredProcedureParameter("to_time", String.class, ParameterMode.IN);
		storedProcedureQuery.setParameter("cpp_code", ticketCriteria.getCppCode());
		storedProcedureQuery.setParameter("from_time", ticketCriteria.getStart_time());
		storedProcedureQuery.setParameter("to_time", ticketCriteria.getEnd_time());
		storedProcedureQuery.execute();
		List<DailyTicketRevenueDto> result = new ArrayList<>();
		List<Object[]> lst = storedProcedureQuery.getResultList();
		for (Object[] value : lst) {
			DailyTicketRevenueDto obDailyTicketRevenueDto = new DailyTicketRevenueDto();
			if (value[0] != null)
				obDailyTicketRevenueDto.setCode(value[0].toString());

			if (value[1] != null)
				obDailyTicketRevenueDto.setRevenue((double) value[1]);

			result.add(obDailyTicketRevenueDto);
		}
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		responseApi.setData(result);
		responseApi.setError(errorDto);
		return responseApi;
	}

}

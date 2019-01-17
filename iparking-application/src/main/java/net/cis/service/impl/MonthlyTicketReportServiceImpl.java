package net.cis.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.cis.constants.ResponseErrorCodeConstants;
import net.cis.dto.ErrorDto;
import net.cis.dto.MonthlyTicketReportDto;
import net.cis.dto.MonthlyTicketReportEndPointDto;
import net.cis.dto.ResponseApi;
import net.cis.jpa.criteria.MonthlyTicketReportCriteria;
import net.cis.jpa.entity.MonthlyTicketReportEntity;
import net.cis.jpa.entity.MonthlyTicketReportFooterEntity;
import net.cis.repository.MonthlyTicketReportFooterRepository;
import net.cis.repository.MonthlyTicketReportRepository;
import net.cis.service.MonthlyTicketReportService;


@Service
public class MonthlyTicketReportServiceImpl implements MonthlyTicketReportService{
	
	@Autowired
	MonthlyTicketReportRepository monthlyTicketReportRepository;
	
	@Autowired
	MonthlyTicketReportFooterRepository monthlyTicketReportFooterRepository;

	ModelMapper mapper;
	
	@PostConstruct
	public void initialize() {
		mapper = new ModelMapper();
	}
	
	@Override
	public ResponseApi findAll(MonthlyTicketReportCriteria monthlyTicketReportCriteria, Pageable pageable) {
		// TODO Auto-generated method stub
		List<MonthlyTicketReportEntity> monthlyTicketReportEntities = null;
		MonthlyTicketReportEndPointDto monthlyTicketReportEndPointDto = new MonthlyTicketReportEndPointDto();
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		try {
			monthlyTicketReportEntities = monthlyTicketReportRepository.findAllReport(monthlyTicketReportCriteria.getIs_paid(),
					monthlyTicketReportCriteria.getParking_place(), monthlyTicketReportCriteria.getExpired(),
					monthlyTicketReportCriteria.getPhone(), monthlyTicketReportCriteria.getNumber_plate(),
					monthlyTicketReportCriteria.getFullName(), monthlyTicketReportCriteria.getContract_code(),
					monthlyTicketReportCriteria.getContract_no(), monthlyTicketReportCriteria.getStatus(), monthlyTicketReportCriteria.getValid_from(),
					monthlyTicketReportCriteria.getValid_end(), pageable);
			List<MonthlyTicketReportDto> ticketReportDtos = this.map(monthlyTicketReportEntities);
			MonthlyTicketReportFooterEntity monthlyTicketReportFooterEntity = null;
			try {
				monthlyTicketReportFooterEntity = monthlyTicketReportFooterRepository.findAllReport(monthlyTicketReportCriteria.getIs_paid(),
					monthlyTicketReportCriteria.getParking_place(), monthlyTicketReportCriteria.getExpired(),
					monthlyTicketReportCriteria.getPhone(), monthlyTicketReportCriteria.getNumber_plate(),
					monthlyTicketReportCriteria.getFullName(), monthlyTicketReportCriteria.getContract_code(),
					monthlyTicketReportCriteria.getContract_no(), monthlyTicketReportCriteria.getStatus(), monthlyTicketReportCriteria.getValid_from(),
					monthlyTicketReportCriteria.getValid_end());
				
				if (monthlyTicketReportFooterEntity == null) {
					monthlyTicketReportFooterEntity = new MonthlyTicketReportFooterEntity();
				}
				mapper.map(monthlyTicketReportFooterEntity, monthlyTicketReportEndPointDto);
				monthlyTicketReportEndPointDto.setTicketReportDtos(ticketReportDtos);
				responseApi.setData(monthlyTicketReportEndPointDto);
				errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
				errorDto.setMessage("");
				responseApi.setError(errorDto);
				return responseApi;
			}catch (Exception e) {
				// TODO: handle exception
				errorDto.setCode(ResponseErrorCodeConstants.StatusInternalServerError);
				errorDto.setMessage(ResponseErrorCodeConstants.DBAccessErr);
				responseApi.setError(errorDto);
				return responseApi;
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			errorDto.setCode(ResponseErrorCodeConstants.StatusInternalServerError);
			errorDto.setMessage(ResponseErrorCodeConstants.DBAccessErr);
			responseApi.setError(errorDto);
			return responseApi;
			
		}
	}
	
	private List<MonthlyTicketReportDto> map(List<MonthlyTicketReportEntity> source) {
		ArrayList<MonthlyTicketReportDto> rtn = new ArrayList<>();
		source.stream().map((entity) -> {
			MonthlyTicketReportDto dto = new MonthlyTicketReportDto();
			mapper.map(entity, dto);
			return dto;
		}).forEachOrdered((dto) -> {
			rtn.add(dto);
		});
		return rtn;
	}

}

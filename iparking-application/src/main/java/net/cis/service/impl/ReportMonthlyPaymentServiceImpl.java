package net.cis.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cis.dto.ReportMonthlyPaymentDto;
import net.cis.jpa.entity.ReportMonthlyPaymentEntity;
import net.cis.repository.ReportMonthlyPaymentRepository;
import net.cis.service.ReportMonthlyPaymentService;

@Service
public class ReportMonthlyPaymentServiceImpl implements ReportMonthlyPaymentService {
	@Autowired
	ReportMonthlyPaymentRepository reportMonthlyPaymentRepository;

	ModelMapper mapper;

	@PostConstruct
	public void initialize() {
		mapper = new ModelMapper();

	}

	@Override
	public List<ReportMonthlyPaymentDto> getSummaryReportMonthlyPaymentByParkingPlace(List<Long> cppId, Long fromDate,
			Long toDate) {
		List<ReportMonthlyPaymentEntity> entities = reportMonthlyPaymentRepository
				.getSummaryReportMonthlyPaymentEntity(cppId, fromDate, toDate);
		return this.map(entities);
	}

	private List<ReportMonthlyPaymentDto> map(List<ReportMonthlyPaymentEntity> source) {
		ArrayList<ReportMonthlyPaymentDto> rtn = new ArrayList<>();
		source.stream().map((entity) -> {
			ReportMonthlyPaymentDto dto = new ReportMonthlyPaymentDto();
			mapper.map(entity, dto);
			return dto;
		}).forEachOrdered((dto) -> {
			rtn.add(dto);
		});
		return rtn;
	}

}

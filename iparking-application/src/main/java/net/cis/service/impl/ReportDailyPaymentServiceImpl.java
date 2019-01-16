package net.cis.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cis.dto.ReportDailyPaymentDto;
import net.cis.jpa.entity.ReportDailyPaymentEntity;
import net.cis.repository.ReportDailyPaymentRepository;
import net.cis.service.ReportDailyPaymentServic;

@Service
public class ReportDailyPaymentServiceImpl implements ReportDailyPaymentServic {
	@Autowired
	ReportDailyPaymentRepository reportDailyPaymentRepository;

	ModelMapper mapper;

	@PostConstruct
	public void initialize() {
		mapper = new ModelMapper();

	}

	@Override
	public List<ReportDailyPaymentDto> getSummaryReportDailyPaymentByParkingPlace(List<Long> cppId, Long fromDate,
			Long toDate) {
		List<ReportDailyPaymentEntity> entities = reportDailyPaymentRepository.getSummaryReportDailyPaymentEntity(cppId,
				fromDate, toDate);
		return this.map(entities);
	}

	private List<ReportDailyPaymentDto> map(List<ReportDailyPaymentEntity> source) {
		ArrayList<ReportDailyPaymentDto> rtn = new ArrayList<>();
		source.stream().map((entity) -> {
			ReportDailyPaymentDto dto = new ReportDailyPaymentDto();
			mapper.map(entity, dto);
			return dto;
		}).forEachOrdered((dto) -> {
			rtn.add(dto);
		});
		return rtn;
	}

}

package net.cis.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cis.dto.ReportProportionPaymentDto;
import net.cis.repository.ReportProportionPaymentRepository;
import net.cis.service.ReportProportionPaymentService;

@Service
public class ReportProportionPaymentServiceImpl implements ReportProportionPaymentService {
	@Autowired
	private ReportProportionPaymentRepository proportionPaymentRepository;
	ModelMapper mapper;

	@PostConstruct
	public void initialize() {
		mapper = new ModelMapper();

	}

	@Override
	public List<ReportProportionPaymentDto> getProportionPayment(List<Long> lstCppId, long fromDate, long toDate) {
		List<ReportProportionPaymentDto> entities = proportionPaymentRepository.findByProportionPaymentEntity(lstCppId,
				fromDate, toDate);
		return entities;
	}

	@Override
	public ReportProportionPaymentDto getProportionPayment(long cppId, long fromDate, long toDate) {
		ReportProportionPaymentDto entities = proportionPaymentRepository.findByProportionPaymentEntity(cppId, fromDate,
				toDate);
		return entities;
	}

}

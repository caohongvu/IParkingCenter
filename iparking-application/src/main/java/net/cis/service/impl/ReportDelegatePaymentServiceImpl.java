package net.cis.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cis.dto.ProportionPaymentDto;
import net.cis.dto.ReportDelegatePaymentDto;
import net.cis.jpa.entity.ProportionPaymentEntity;
import net.cis.jpa.entity.ReportDelegatePaymentEntity;
import net.cis.repository.ProportionPaymentRepository;
import net.cis.repository.ReportDelegatePaymentRepository;
import net.cis.service.ReportDelegatePaymentService;

/**
 * Created by Vincent on 02/10/2018
 */
@Service
public class ReportDelegatePaymentServiceImpl implements ReportDelegatePaymentService {

	@Autowired
	private ReportDelegatePaymentRepository reportDelegatePaymentRepository;

	@Autowired
	private ProportionPaymentRepository proportionPaymentRepository;

	ModelMapper mapper;

	@PostConstruct
	public void initialize() {
		mapper = new ModelMapper();

	}

	@Override
	public List<ReportDelegatePaymentDto> findByCarppIdsAndDate(List<Long> carppIds, String date) {
		List<ReportDelegatePaymentEntity> entities = reportDelegatePaymentRepository
				.findByCarppIdInAndCreatedDate(carppIds, date);
		return this.map(entities);
	}

	private List<ReportDelegatePaymentDto> map(List<ReportDelegatePaymentEntity> source) {

		ArrayList<ReportDelegatePaymentDto> rtn = new ArrayList<>();
		source.stream().map((entity) -> {
			ReportDelegatePaymentDto dto = new ReportDelegatePaymentDto();
			mapper.map(entity, dto);
			return dto;
		}).forEachOrdered((dto) -> {
			rtn.add(dto);
		});
		return rtn;
	}

	@Override
	public List<ProportionPaymentDto> getProportionPayment(List<Long> cppId, String fromDate, String toDate) {
		List<ProportionPaymentEntity> entities = proportionPaymentRepository.findByProportionPaymentEntity(cppId,
				fromDate, toDate);
		return this.map2(entities);
	}

	private List<ProportionPaymentDto> map2(List<ProportionPaymentEntity> source) {
		ArrayList<ProportionPaymentDto> rtn = new ArrayList<>();
		source.stream().map((entity) -> {
			ProportionPaymentDto dto = new ProportionPaymentDto();
			mapper.map(entity, dto);
			return dto;
		}).forEachOrdered((dto) -> {
			rtn.add(dto);
		});
		return rtn;
	}

}

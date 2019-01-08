package net.cis.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cis.dto.ReportDelegatePaymentDto;
import net.cis.jpa.entity.ReportDelegatePaymentEntity;
import net.cis.repository.ReportDelegatePaymentRepository;
import net.cis.service.ReportDelegatePaymentService;

/**
 * Created by Vincent on 02/10/2018
 */
@Service
public class ReportDelegatePaymentServiceImpl implements ReportDelegatePaymentService {

	@Autowired
	private ReportDelegatePaymentRepository reportDelegatePaymentRepository;
	
	ModelMapper mapper;
	
	@Override
	public List<ReportDelegatePaymentDto> findByCarppIdsAndDate(List<Long> carppIds, String date) {
		List<ReportDelegatePaymentEntity> entities = reportDelegatePaymentRepository.findByCarppIdInAndCreatedDate(carppIds, date);
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

	

	@PostConstruct
	public void initialize() {
		mapper = new ModelMapper();
		
	}

}

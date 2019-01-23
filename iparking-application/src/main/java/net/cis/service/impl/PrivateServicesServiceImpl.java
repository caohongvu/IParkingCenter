package net.cis.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cis.dto.PrivateServicesParkingDto;
import net.cis.jpa.entity.PrivateServicesParkingEntity;
import net.cis.repository.PrivateServiceRepository;
import net.cis.repository.PrivateServicesParkingCustomerRepository;
import net.cis.repository.PrivateServicesParkingRepository;
import net.cis.service.PrivateServicesService;

@Service
public class PrivateServicesServiceImpl implements PrivateServicesService {
	@Autowired
	PrivateServiceRepository privateServiceRepository;
	@Autowired
	PrivateServicesParkingCustomerRepository privateServicesParkingCustomerRepository;
	@Autowired
	PrivateServicesParkingRepository privateServicesParkingRepository;
	ModelMapper mapper;

	@PostConstruct
	public void initialize() {
		mapper = new ModelMapper();
	}

	@Override
	public List<PrivateServicesParkingDto> getPrivateServiceParkings(Long parkingId, Integer status) {
		List<PrivateServicesParkingEntity> lst = null;
		if (status == null) {
			lst = privateServicesParkingRepository.findByParkingId(parkingId);
		} else {
			lst = privateServicesParkingRepository.findByParkingIdAndStatus(parkingId, status);
		}
		return this.map(lst);
	}

	private List<PrivateServicesParkingDto> map(List<PrivateServicesParkingEntity> source) {
		List<PrivateServicesParkingDto> rtn = new ArrayList<>();
		source.stream().map((entity) -> {
			PrivateServicesParkingDto dto = new PrivateServicesParkingDto();
			mapper.map(entity, dto);
			return dto;
		}).forEachOrdered((dto) -> {
			rtn.add(dto);
		});
		return rtn;
	}

}

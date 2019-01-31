package net.cis.service.impl;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cis.dto.CustomerRecoveryDto;
import net.cis.jpa.entity.CustomerRecoveryEntity;
import net.cis.repository.CustomerRecoveryRepository;
import net.cis.service.CustomerRecoveryService;

@Service
public class CustomerRecoveryServiceImpl implements CustomerRecoveryService {
	@Autowired
	private CustomerRecoveryRepository customerRecoveryRepository;
	ModelMapper mapper;

	@PostConstruct
	public void initialize() {
		mapper = new ModelMapper();
	}

	@Override
	public CustomerRecoveryDto save(CustomerRecoveryDto customerRecoveryDto) {
		CustomerRecoveryEntity entity = new CustomerRecoveryEntity();
		mapper.map(customerRecoveryDto, entity);
		mapper.map(customerRecoveryRepository.save(entity), customerRecoveryDto);
		return customerRecoveryDto;
	}
}

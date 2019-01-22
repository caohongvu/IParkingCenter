package net.cis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cis.repository.PrivateServiceRepository;
import net.cis.repository.PrivateServicesParkingCustomerRepository;
import net.cis.repository.PrivateServicesParkingRepository;

@Service
public class PrivateServicesServiceImpl implements net.cis.service.PrivateServicesService {
	@Autowired
	PrivateServiceRepository privateServiceRepository;
	@Autowired
	PrivateServicesParkingCustomerRepository privateServicesParkingCustomerRepository;
	@Autowired
	PrivateServicesParkingRepository privateServicesParkingRepository;
	
	
}

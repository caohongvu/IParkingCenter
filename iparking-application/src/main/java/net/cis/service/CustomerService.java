package net.cis.service;

import java.util.List;
import java.util.Map;

import net.cis.dto.CustomerCarDto;
import net.cis.dto.CustomerDto;
import net.cis.dto.CustomerInfoDto;
import net.cis.jpa.entity.CustomerEntity;

public interface CustomerService {
	Map<String, Object> createCustomerInPoseidonDb(String phone) throws Exception;

	CustomerEntity createCustomerInIparkingCenter(CustomerEntity customerEntity);

	List<CustomerCarDto> findCustomerCarByNumberPlate(String numberPlate, int verified) throws Exception;

	CustomerInfoDto findCustomerInfoByCusId(long cusId) throws Exception;
	
	CustomerDto findCustomerByOldId(long cusId) throws Exception;
}

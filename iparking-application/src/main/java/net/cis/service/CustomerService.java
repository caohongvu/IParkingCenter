package net.cis.service;

import java.util.List;
import java.util.Map;

import net.cis.dto.CustomerCarDto;
import net.cis.dto.CustomerDto;
import net.cis.dto.CustomerInfoDto;

public interface CustomerService {
	Map<String, Object> saveCustomerInPoseidonDb(String phone) throws Exception;

	CustomerDto saveCustomerInIparkingCenter(CustomerDto customerDto);

	List<CustomerCarDto> findCustomerCarByNumberPlate(String numberPlate, Integer verified) throws Exception;

	CustomerInfoDto findCustomerInfoByCusId(long cusId) throws Exception;

	CustomerDto findCustomerByOldId(long cusId) throws Exception;

	CustomerDto findByPhone2(String phone) throws Exception;

	CustomerInfoDto saveCustomerInfoEntity(CustomerInfoDto objCustomerInfoDto) throws Exception;

	CustomerCarDto saveCustomerCarEntity(CustomerCarDto objCustomerCarDto) throws Exception;

	void saveCustomerInfoInPoseidonDb(long cusId,String phone, String email) throws Exception;
}

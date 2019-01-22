package net.cis.service;

import java.util.List;
import java.util.Map;

import net.cis.dto.CustomerCarDto;
import net.cis.dto.CustomerDto;
import net.cis.dto.CustomerInfoDto;

public interface CustomerService {
	Map<String, Object> saveCustomerInPoseidonDb(String phone) throws Exception;

	Map<String, Object> saveCustomerCarInPoseidonDb(long cusId, String numberPlate, int carType) throws Exception;

	CustomerDto saveCustomerInIparkingCenter(CustomerDto customerDto);

	List<CustomerCarDto> findCustomerCarByNumberPlate(String numberPlate) throws Exception;

	CustomerCarDto findCustomerCarByNumberPlateAndCusId(String numberPlate, long cusId) throws Exception;

	CustomerInfoDto findCustomerInfoByCusId(long cusId) throws Exception;

	CustomerDto findCustomerByOldId(long cusId) throws Exception;

	CustomerDto findByPhone2(String phone) throws Exception;

	CustomerInfoDto saveCustomerInfoEntity(CustomerInfoDto objCustomerInfoDto) throws Exception;

	CustomerCarDto saveCustomerCarEntity(CustomerCarDto objCustomerCarDto) throws Exception;

	void saveCustomerInfoInPoseidonDb(long cusId, String phone, String email) throws Exception;

	CustomerCarDto findCustomerCarById(long id) throws Exception;

	void deleteCustomerCar(long id) throws Exception;

	CustomerDto findById(long cusId) throws Exception;

	Map<String, Object> getCapcha(String captchaID) throws Exception;

	Map<String, Object> otpSignupCallGolang(String phone, String captcha, String captchaID) throws Exception;

	Map<String, Object> napSignupCallGolang(String phone, String ticket, String otp) throws Exception;

	Map<String, Object> saveCustomerInfoInPoseidonDbReturnObject(long cusId, String phone, String email)
			throws Exception;
}

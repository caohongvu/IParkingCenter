package net.cis.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import net.cis.dto.CustomerCarDto;
import net.cis.dto.CustomerDto;
import net.cis.dto.CustomerInfoDto;
import net.cis.dto.MenuDto;

public interface CustomerService {
	Map<String, Object> createCustomerInShardDb(String phone) throws Exception;

	Map<String, Object> updateCustomerInShardDb(String cusId, String password) throws Exception;

	Map<String, Object> createCustomerCarInShardDb(long cusId, String numberPlate, int carType) throws Exception;

	CustomerDto saveCustomerInIparkingCenter(CustomerDto customerDto);

	List<CustomerCarDto> findCustomerCarByNumberPlate(String numberPlate) throws Exception;

	CustomerCarDto findCustomerCarByNumberPlateAndCusId(String numberPlate, long cusId) throws Exception;

	CustomerInfoDto findCustomerInfoByCusId(long cusId) throws Exception;

	CustomerInfoDto findCustomerInfoByEmail(String email) throws Exception;

	CustomerDto findCustomerByOldId(long cusId) throws Exception;

	CustomerDto findByPhone2(String phone) throws Exception;

	CustomerInfoDto saveCustomerInfoEntity(CustomerInfoDto objCustomerInfoDto) throws Exception;

	CustomerCarDto saveCustomerCarEntity(CustomerCarDto objCustomerCarDto) throws Exception;

	void saveCustomerInfoInPoseidonDb(long cusId, String phone, String email) throws Exception;

	CustomerCarDto findCustomerCarById(long id) throws Exception;

	void deleteCustomerCar(long id) throws Exception;

	CustomerDto findById(long cusId) throws Exception;

	InputStream getCapcha(String captchaID) throws Exception;

	Map<String, Object> otpSignupCallGolang(String phone, String captcha, String captchaID) throws Exception;

	Map<String, Object> verifyOtpSignupCallGolang(String phone, String otp, String ticket) throws Exception;

	Map<String, Object> napSignupCallGolang(String phone, String password) throws Exception;

	Map<String, Object> saveCustomerInfoInPoseidonDbReturnObject(long cusId, String phone, String email)
			throws Exception;

	List<MenuDto> getMenuByRoleForWeb(Integer roleId);
}

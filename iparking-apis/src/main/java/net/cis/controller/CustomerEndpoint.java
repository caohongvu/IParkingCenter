package net.cis.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.cis.common.util.DateTimeUtil;
import net.cis.common.util.MessageUtil;
import net.cis.common.util.Utils;
import net.cis.common.util.constant.UserConstant;
import net.cis.dto.CustomerCarDto;
import net.cis.dto.CustomerDto;
import net.cis.dto.CustomerInfoDto;
import net.cis.dto.ResponseApi;
import net.cis.dto.ResponseDto;
import net.cis.repository.CustomerInfoRepository;
import net.cis.service.CustomerService;

/**
 * 
 * @author liemnh
 *
 */
@RestController
@RequestMapping("/customer")
@Api(value = "customer Endpoint", description = "The URL to handle customer endpoint")
public class CustomerEndpoint {
	protected final Logger LOGGER = Logger.getLogger(getClass());
	@Autowired
	CustomerService customerService;
	@Autowired
	CustomerInfoRepository customerInfoRepository;

	/**
	 * liemnh
	 * 
	 * @param numberPlate
	 * @return
	 */
	@RequestMapping(value = "/find-by-numberplate", method = RequestMethod.GET)
	@ApiOperation("Get detail customer by numberplate")
	public @ResponseBody ResponseDto getById(@RequestParam(name = "numberPlate") String numberPlate) {
		ResponseDto responseDto = new ResponseDto();
		responseDto.setCode(HttpStatus.OK.toString());
		try {
			if (!Utils.validateNumberPlate(numberPlate)) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage("Biển số xe sai định dạng");
				return responseDto;
			}
			// thuc hien lay thong tin customer tu bien so xe
			List<CustomerCarDto> lstCustomerCarDto = customerService
					.findCustomerCarByNumberPlateAndVerified(numberPlate, UserConstant.STATUS_VERIFIED);
			if (lstCustomerCarDto != null && lstCustomerCarDto.size() == 1) {
				CustomerCarDto objCustomerCarDto = lstCustomerCarDto.get(0);
				CustomerInfoDto objCustomerInfoDto = customerService
						.findCustomerInfoByCusId(objCustomerCarDto.getCustomer());
				CustomerDto objCustomerDto = customerService.findCustomerByOldId(objCustomerCarDto.getCustomer());
				Object dataObject = new Object() {
					public long id = objCustomerCarDto.getCustomer();
					public String phone = objCustomerDto.getPhone();
					public String phone2 = objCustomerDto.getPhone2();
					public int status = objCustomerDto.getStatus();
					public String numberPlate = objCustomerCarDto.getNumberPlate();
					public String email = objCustomerInfoDto.getEmail();
				};
				responseDto.setData(dataObject);
				return responseDto;
			}
			return responseDto;
		} catch (Exception ex) {
			LOGGER.error("Lỗi hệ thống: " + ex.getMessage());
			responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
			responseDto.setMessage(ex.getMessage());
			return responseDto;
		}

	}

	/**
	 * liemnh
	 * 
	 * @param cusId
	 * @param email
	 * @param verificationCode
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/customer-info-update", method = RequestMethod.POST)
	@ApiOperation("Create or update customer info")
	public @ResponseBody ResponseDto updateCustomerInfo(@RequestParam(name = "cus_id") long cusId,
			@RequestParam(name = "email") String email,
			@RequestParam(name = "verification_code", required = false) String verificationCode,
			@RequestParam(name = "status", required = false) Integer status) {
		ResponseDto responseDto = new ResponseDto();
		responseDto.setCode(HttpStatus.OK.toString());
		try {
			// tim kiem CustomerInfo from db
			CustomerInfoDto objCustomerInfoDtoInDB = customerService.findCustomerInfoByCusId(cusId);
			if (objCustomerInfoDtoInDB != null) {
				// thuc hien cap nhat customer_info
				objCustomerInfoDtoInDB.setEmail(email);
				if (!StringUtils.isEmpty(verificationCode))
					objCustomerInfoDtoInDB.setVerificationCode(verificationCode);
				if (status != null)
					objCustomerInfoDtoInDB.setStatus(status);
				customerService.saveCustomerInfoEntity(objCustomerInfoDtoInDB);
				return responseDto;
			}

			CustomerInfoDto objCustomerInfoDto = new CustomerInfoDto();
			objCustomerInfoDto.setCusId(cusId);
			objCustomerInfoDto.setEmail(email);
			if (!StringUtils.isEmpty(verificationCode))
				objCustomerInfoDto.setVerificationCode(verificationCode);
			if (status != null)
				objCustomerInfoDto.setStatus(status);
			objCustomerInfoDto.setCreatedAt(DateTimeUtil.getCurrentDateTime());
			customerService.saveCustomerInfoEntity(objCustomerInfoDto);
			return responseDto;
		} catch (Exception ex) {
			LOGGER.error("Lỗi hệ thống: " + ex.getMessage());
			responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
			return responseDto;
		}
	}

	/**
	 * liemnh
	 * 
	 * @param id
	 * @param cusId
	 * @param numberPlate
	 * @return
	 */
	@RequestMapping(value = "/customer-car-update", method = RequestMethod.POST)
	@ApiOperation("create or update customer car")
	public @ResponseBody ResponseDto updateCustomerInfo(@RequestParam(name = "id") long id,
			@RequestParam(name = "cus_id") long cusId, @RequestParam(name = "number_plate") String numberPlate) {
		ResponseDto responseDto = new ResponseDto();
		try {
			// tim kiem customer
			CustomerDto objCustomerDto = customerService.findCustomerByOldId(cusId);
			// tim kiem CustomerInfo from db
			if (objCustomerDto == null) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage("Không tồn tại customer");
			}
			CustomerCarDto objCustomerCarDto = customerService.findCustomerCarByNumberPlateAndCusId(numberPlate, cusId);
			if (objCustomerCarDto == null) {
				objCustomerCarDto = new CustomerCarDto();
				objCustomerCarDto.setCustomer(cusId);
				objCustomerCarDto.setNumberPlate(numberPlate);
				objCustomerCarDto.setCreatedAt(DateTimeUtil.getCurrentDateTime());
				objCustomerCarDto.setUpdatedAt(DateTimeUtil.getCurrentDateTime());
				customerService.saveCustomerCarEntity(objCustomerCarDto);
				responseDto.setCode(HttpStatus.OK.toString());
				return responseDto;
			}
			responseDto.setCode(HttpStatus.OK.toString());
			return responseDto;
		} catch (Exception ex) {
			LOGGER.error("Lỗi hệ thống: " + ex.getMessage());
			responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
			return responseDto;
		}
	}

	/**
	 * liemnh
	 * 
	 * @param id
	 * @param cusId
	 * @return
	 */
	@RequestMapping(value = "/customer-car-delete", method = RequestMethod.POST)
	@ApiOperation("delete customer car")
	public @ResponseBody ResponseDto updateCustomerInfo(@RequestParam(name = "id") long id,
			@RequestParam(name = "cus_id") long cusId) {
		ResponseDto responseDto = new ResponseDto();
		try {
			// tim kiem theo id
			CustomerCarDto objCustomerCarDto = customerService.findCustomerCarById(id);
			if (objCustomerCarDto == null) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage(MessageUtil.MESSAGE_CUSTOMER_CAR_NOT_EXITS);
				return responseDto;
			}
			if (objCustomerCarDto.getCustomer() == cusId) {
				customerService.deleteCustomerCar(id);
				responseDto.setCode(HttpStatus.OK.toString());
				return responseDto;
			} else {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage(MessageUtil.MESSAGE_CUSTOMER_NOT_EXITS);
				return responseDto;
			}
		} catch (Exception ex) {
			LOGGER.error("Lỗi hệ thống: " + ex.getMessage());
			responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
			return responseDto;
		}
	}

	/**
	 * liemnh
	 * 
	 * @param cusId
	 * @param email
	 * @param verificationCode
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/customer-create", method = RequestMethod.POST)
	@ApiOperation("Create or update customer info")
	public @ResponseBody ResponseDto createCustomer(@RequestParam(name = "phone") String phone,
			@RequestParam(name = "phone2") String phone2,
			@RequestParam(name = "password") String password,
			@RequestParam(name = "telco") String telco,
			@RequestParam(name = "status_reason") String status_reason,
			@RequestParam(name = "checksum") String checkSum, 
			@RequestParam(name = "status") int status,
			@RequestParam(name = "id") long old_id) {
		ResponseDto responseDto = new ResponseDto();
		responseDto.setCode(HttpStatus.OK.toString());
		try {
			//kiem tra so dien thoai 
			if(!Utils.validateVNPhoneNumber(phone)){
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage("Phone Malformed");
				return responseDto;
			}
			
			CustomerDto objCustomerDto = customerService.findCustomerByOldId(old_id);
			if (objCustomerDto != null) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage("Customer exits");
				return responseDto;
			}

			objCustomerDto = customerService.findByPhone2(phone);
			if (objCustomerDto != null) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage("Customer by phone exits");
				return responseDto;
			}
			objCustomerDto = new CustomerDto();
			objCustomerDto.setPhone(phone);
			objCustomerDto.setPhone2(phone);
			objCustomerDto.setTelco(telco);
			objCustomerDto.setPassword(password.getBytes());
			objCustomerDto.setCheckSum(checkSum);
			objCustomerDto.setStatus(status);
			objCustomerDto.setOldId(old_id);
			objCustomerDto.setCreatedAt(DateTimeUtil.getCurrentDateTime());
			objCustomerDto.setUpdatedAt(DateTimeUtil.getCurrentDateTime());
			objCustomerDto = customerService.saveCustomerInIparkingCenter(objCustomerDto);
			responseDto.setData(objCustomerDto);
			return responseDto;
		} catch (Exception ex) {
			LOGGER.error("Lỗi hệ thống: " + ex.getMessage());
			responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
			return responseDto;
		}
	}
	
	
	@RequestMapping(value = "/otp/signup", method = RequestMethod.POST)
	@ApiOperation("signup customer")
	public @ResponseBody Object otpSignUp(@RequestParam(name = "phone", required =  true) String phone,
			@RequestParam(name = "captchaID" , required =  true) String captchaID,
			@RequestParam(name = "captcha" , required =  true) String captcha) {
		ResponseDto responseDto = new ResponseDto();
		try {
			if (StringUtils.isEmpty(captchaID)) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage(MessageUtil.MESSAGE_CANNOT_CHECK_CAPTCHA);
				return responseDto;
			}
			if (StringUtils.isEmpty(captcha)) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage(MessageUtil.MESSAGE_CANNOT_CHECK_CAPTCHA);
				return responseDto;
			}
			
			if (StringUtils.isEmpty(phone)) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage(MessageUtil.MESSAGE_PHONE_WRONG_FORMAT);
				return responseDto;
			}
			if(!Utils.validateVNPhoneNumber(String.valueOf(phone))){
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage("Phone Malformed");
				return responseDto;
			}
			Map<String, Object> result = customerService.otpSignupCallGolang(phone, captcha, captchaID);
			if (result == null || !HttpStatus.OK.toString().equals(result.get("Code"))) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage("Lỗi tạo otp");
				return responseDto;
			}
			responseDto.setCode(HttpStatus.OK.toString());
			responseDto.setData(result);
			return responseDto;
		}catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("Lỗi hệ thống: " + e.getMessage());
			responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
			return responseDto;
		}
	}
	
	@RequestMapping(value = "/nap/signup", method = RequestMethod.POST)
	@ApiOperation("signup customer")
	public @ResponseBody Object napSignUp(@RequestParam(name = "phone", required =  true) String phone,
			@RequestParam(name = "ticket" , required =  true) String ticket,
			@RequestParam(name = "otp" , required =  true) String otp) {
		ResponseDto responseDto = new ResponseDto();
		try {
			if (StringUtils.isEmpty(otp)) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage(MessageUtil.MESSAGE_CANNOT_OTP);
				return responseDto;
			}
			if (StringUtils.isEmpty(ticket)) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage(MessageUtil.MESSAGE_CANNOT_TICKET);
				return responseDto;
			}
			
			if (StringUtils.isEmpty(phone)) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage(MessageUtil.MESSAGE_PHONE_WRONG_FORMAT);
				return responseDto;
			}
			if(!Utils.validateVNPhoneNumber(String.valueOf(phone))){
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage("Phone Malformed");
				return responseDto;
			}
			Map<String, Object> result = customerService.napSignupCallGolang(phone, ticket, otp);
			if (result == null || !HttpStatus.OK.toString().equals(result.get("Code"))) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage("Lỗi tạo token");
				return responseDto;
			}
			responseDto.setCode(HttpStatus.OK.toString());
			responseDto.setMessage(result.get("Message").toString());
			responseDto.setData(result.get("Token"));
			return responseDto;
		}catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("Lỗi hệ thống: " + e.getMessage());
			responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
			return responseDto;
		}
	}

}

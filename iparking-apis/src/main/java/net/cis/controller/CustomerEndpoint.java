package net.cis.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.cis.common.util.DateTimeUtil;
import net.cis.common.util.MD5Util;
import net.cis.common.util.MessageUtil;
import net.cis.common.util.PasswordGenerator;
import net.cis.common.util.Utils;
import net.cis.common.util.constant.URLConstants;
import net.cis.common.util.constant.UserConstant;
import net.cis.constants.CustomerConstans;
import net.cis.constants.ResponseErrorCodeConstants;
import net.cis.dto.CarProfileDto;
import net.cis.dto.CustomerCarDto;
import net.cis.dto.CustomerDto;
import net.cis.dto.CustomerInfoDto;
import net.cis.dto.CustomerRecoveryDto;
import net.cis.dto.ErrorDto;
import net.cis.dto.MenuDto;
import net.cis.dto.ResponseApi;
import net.cis.dto.ResponseDto;
import net.cis.dto.UserInfo;
import net.cis.repository.CustomerInfoRepository;
import net.cis.security.filter.TokenAuthenticationService;
import net.cis.service.CarProfileService;
import net.cis.service.CustomerRecoveryService;
import net.cis.service.CustomerService;
import net.cis.service.EmailService;

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

	@Autowired
	CarProfileService carProfileService;

	@Autowired
	EmailService emailService;

	@Autowired
	CustomerRecoveryService customerRecoveryService;

	/**
	 * liemnh
	 * 
	 * @param numberPlate
	 * @return
	 */
	@RequestMapping(value = "/find-by-numberplate", method = RequestMethod.GET)
	@ApiOperation("Get detail customer by numberplate")
	public @ResponseBody ResponseApi getById(@RequestParam(name = "numberPlate") String numberPlate) {
		ResponseApi responseDto = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		try {
			if (!Utils.validateNumberPlate(numberPlate)) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("Biển số xe sai định dạng");
				responseDto.setError(errorDto);
				return responseDto;
			}
			// thuc hien lay thong tin customer tu bien so xe
			List<CustomerCarDto> lstCustomerCarDto = customerService.findCustomerCarByNumberPlate(numberPlate);

			if (lstCustomerCarDto == null || lstCustomerCarDto.size() == 0) {
				responseDto.setError(errorDto);
				responseDto.setData(lstCustomerCarDto);
				return responseDto;
			}
			List<Object> lstResult = new ArrayList<Object>();
			for (CustomerCarDto objCustomerCarDto : lstCustomerCarDto) {
				CustomerInfoDto objCustomerInfoDto = customerService
						.findCustomerInfoByCusId(objCustomerCarDto.getCustomer());
				CustomerDto objCustomerDto = customerService.findCustomerByOldId(objCustomerCarDto.getCustomer());
				Map<String, Object> map = new HashMap<>();
				map.put("id", objCustomerCarDto.getCustomer());
				map.put("phone", objCustomerDto.getPhone());
				map.put("phone2", objCustomerDto.getPhone2());
				map.put("status", objCustomerDto.getStatus());
				map.put("numberPlate", objCustomerCarDto.getNumberPlate());
				map.put("email", objCustomerInfoDto == null ? null : objCustomerInfoDto.getEmail());
				lstResult.add(map);
			}
			responseDto.setData(lstResult);
			responseDto.setError(errorDto);
			return responseDto;
		} catch (Exception ex) {
			LOGGER.error("Lỗi hệ thống: " + ex.getMessage());
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage(ex.getMessage());
			responseDto.setError(errorDto);
			return responseDto;
		}

	}

	/**
	 * liemnh Thuc hien goi sang golang service de cap nhat hoac them moi
	 * customer info sau do cap nhat hoac them moi ben iparking
	 * 
	 * @param cusId
	 * @param email
	 * @param verificationCode
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/customer-info-update/sync", method = RequestMethod.POST)
	@ApiOperation("Create or update customer info")
	public @ResponseBody ResponseApi updateCustomerInfoSync(@RequestParam(name = "cus_id") long cusId,
			@RequestParam(name = "email") String email, @RequestParam(name = "phone") String phone,
			@RequestParam(name = "status", required = false) Integer status) {
		ResponseApi responseDto = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		try {
			// tim kiem CustomerInfo from db
			CustomerInfoDto objCustomerInfoDtoInDB = customerService.findCustomerInfoByCusId(cusId);
			if (objCustomerInfoDtoInDB != null) {
				// thuc hien cap nhat customer_info
				objCustomerInfoDtoInDB.setEmail(email);
				if (status != null)
					objCustomerInfoDtoInDB.setStatus(status);
				if (StringUtils.isEmpty(phone)) {
					errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
					errorDto.setMessage(MessageUtil.MESSAGE_PHONE_WRONG_FORMAT);
					responseDto.setError(errorDto);
					return responseDto;
				}
				if (!Utils.validateVNPhoneNumber(String.valueOf(phone))) {
					errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
					errorDto.setMessage("Phone Malformed");
					responseDto.setError(errorDto);
					return responseDto;
				}
				Map<String, Object> result = customerService.saveCustomerInfoInPoseidonDbReturnObject(cusId, phone,
						email);
				objCustomerInfoDtoInDB.setStatus(Integer.parseInt(result.get("Status").toString()));
				objCustomerInfoDtoInDB.setVerificationCode(result.get("VerificationCode").toString());
				objCustomerInfoDtoInDB = customerService.saveCustomerInfoEntity(objCustomerInfoDtoInDB);
				responseDto.setError(errorDto);
				responseDto.setData(objCustomerInfoDtoInDB);
				return responseDto;
			}

			CustomerInfoDto objCustomerInfoDto = new CustomerInfoDto();
			objCustomerInfoDto.setCusId(cusId);
			objCustomerInfoDto.setEmail(email);
			if (status != null)
				objCustomerInfoDto.setStatus(status);
			if (StringUtils.isEmpty(phone)) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage(MessageUtil.MESSAGE_PHONE_WRONG_FORMAT);
				responseDto.setError(errorDto);
				return responseDto;
			}
			if (!Utils.validateVNPhoneNumber(String.valueOf(phone))) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("Phone Malformed");
				responseDto.setError(errorDto);
				return responseDto;
			}
			Map<String, Object> result = customerService.saveCustomerInfoInPoseidonDbReturnObject(cusId, phone, email);
			objCustomerInfoDto.setStatus(Integer.parseInt(result.get("Status").toString()));
			objCustomerInfoDto.setVerificationCode(result.get("VerificationCode").toString());
			objCustomerInfoDto.setCreatedAt(DateTimeUtil.getCurrentDateTime());
			objCustomerInfoDto = customerService.saveCustomerInfoEntity(objCustomerInfoDto);
			responseDto.setError(errorDto);
			responseDto.setData(objCustomerInfoDto);
			return responseDto;
		} catch (Exception ex) {
			LOGGER.error("Lỗi hệ thống: " + ex.getMessage());
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage(ex.getMessage());
			responseDto.setError(errorDto);
			return responseDto;
		}
	}

	/**
	 * Phuc vu ben golang goi truc tiep sang ben iparking de cap nhat hoac them
	 * moi
	 * 
	 * @param cusId
	 * @param email
	 * @param verificationCode
	 * @param gender
	 * @param full_name
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/customer-info-update", method = RequestMethod.POST)
	@ApiOperation("Create or update customer info")
	public @ResponseBody ResponseApi updateCustomerInfo(@RequestParam(name = "cus_id") long cusId,
			@RequestParam(name = "email") String email,
			@RequestParam(name = "verification_code", required = false) String verificationCode,
			@RequestParam(name = "gender", required = false) Long gender,
			@RequestParam(name = "full_name", required = false) String full_name,
			@RequestParam(name = "status", required = false) Integer status) {
		ResponseApi responseDto = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
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
				if (gender != null)
					objCustomerInfoDtoInDB.setGender(gender);
				if (!StringUtils.isEmpty(full_name))
					objCustomerInfoDtoInDB.setFullName(full_name);
				objCustomerInfoDtoInDB = customerService.saveCustomerInfoEntity(objCustomerInfoDtoInDB);
				responseDto.setError(errorDto);
				responseDto.setData(objCustomerInfoDtoInDB);
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
			objCustomerInfoDto = customerService.saveCustomerInfoEntity(objCustomerInfoDto);
			responseDto.setError(errorDto);
			responseDto.setData(objCustomerInfoDto);
			return responseDto;
		} catch (Exception ex) {
			LOGGER.error("Lỗi hệ thống: " + ex.getMessage());
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage(ex.getMessage());
			responseDto.setError(errorDto);
			return responseDto;
		}
	}

	/**
	 * liemnh Thuc hien goi golang service de cap nhat hoac them moi sau do cap
	 * nhat hoac them moi ben iparking
	 * 
	 * @param id
	 * @param cusId
	 * @param numberPlate
	 * @return
	 */
	@RequestMapping(value = "/create-customer-car", method = RequestMethod.POST)
	@ApiOperation("create or update customer car")
	public @ResponseBody ResponseApi createCustomerCarForApp(@RequestParam(name = "cus_id") long cusId,
			@RequestParam(name = "number_plate") String numberPlate, @RequestParam(name = "seat") int seat,
			@RequestParam(name = "car_type") int carType) {
		/**
		 * Thưc hiện gọi sang API /p/add/car bên golang để thực hiện thêm mới
		 * Nhận kết quả thêm mơi và insert vào DB iparking_center
		 */
		ResponseApi responseDto = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		try {
			// tim kiem customer
			CustomerDto objCustomerDto = customerService.findCustomerByOldId(cusId);
			if (objCustomerDto == null) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("Không tồn tại customer");
				responseDto.setError(errorDto);
				return responseDto;
			}
			// thuc hien goi sang API golang de tao customer_car
			Map<String, Object> map = customerService.createCustomerCarInShardDb(cusId, numberPlate, carType);

			if (map == null || !HttpStatus.OK.toString().equals(map.get("Code"))) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("Lỗi tạo Customer Car In PoseidonDb");
				responseDto.setError(errorDto);
				return responseDto;
			}
			String pClass = (String) map.get("p_class");
			// thuc hien kiem tra car_profile
			CarProfileDto objCarProfileDto = carProfileService.findByNumberPlateAndSeatsAndPClass(numberPlate, seat,
					pClass);

			if (objCarProfileDto == null) {
				// thuc hien tao car profile
				objCarProfileDto = new CarProfileDto();
				objCarProfileDto.setNumberPlate(numberPlate);
				objCarProfileDto.setSeats(seat);
				objCarProfileDto.setpClass(pClass);
				objCarProfileDto = carProfileService.save(objCarProfileDto);
			}

			CustomerCarDto objCustomerCarDto = new CustomerCarDto();
			objCustomerCarDto.setId((long) map.get("cus_car_id"));
			objCustomerCarDto.setNumberPlate((String) map.get("number_plate"));
			objCustomerCarDto.setCustomer((long) map.get("cus_id"));
			objCustomerCarDto.setCreatedAt(DateTimeUtil.getCurrentDateTime());
			objCustomerCarDto.setUpdatedAt(DateTimeUtil.getCurrentDateTime());
			objCustomerCarDto = customerService.saveCustomerCarEntity(objCustomerCarDto);

			responseDto.setError(errorDto);
			responseDto.setData(objCustomerCarDto);
			return responseDto;

		} catch (Exception ex) {
			LOGGER.error("Lỗi hệ thống: " + ex.getMessage());
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage(ex.getMessage());
			responseDto.setError(errorDto);
			return responseDto;
		}
	}

	/**
	 * liemnh Ben golang service se thuc hien goi truc tiep de cap nhat hoac
	 * them moi customer car
	 * 
	 * @param id
	 * @param cusId
	 * @param numberPlate
	 * @return
	 */
	@RequestMapping(value = "/create-customer-car-golang", method = RequestMethod.POST)
	@ApiOperation("create or update customer car")
	public @ResponseBody ResponseApi createCustomerCarForGolang(@RequestParam(name = "id") long id,
			@RequestParam(name = "cus_id") long cusId, @RequestParam(name = "number_plate") String numberPlate,
			@RequestParam(name = "seat") int seat, @RequestParam(name = "p_class") String pClass,
			@RequestParam(name = "status") int status) {
		/**
		 * Golang service gọi để tạo customer_car
		 */
		ResponseApi responseDto = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		try {
			// tim kiem customer
			CustomerDto objCustomerDto = customerService.findCustomerByOldId(cusId);
			if (objCustomerDto == null) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("Không tồn tại customer");
				responseDto.setError(errorDto);
				return responseDto;
			}
			// thuc hien kiem tra car_profile
			CarProfileDto objCarProfileDto = carProfileService.findByNumberPlateAndSeatsAndPClass(numberPlate, seat,
					pClass);

			if (objCarProfileDto == null) {
				// thuc hien tap car profile
				objCarProfileDto = new CarProfileDto();
				objCarProfileDto.setNumberPlate(numberPlate);
				objCarProfileDto.setSeats(seat);
				objCarProfileDto.setpClass(pClass);
				objCarProfileDto = carProfileService.save(objCarProfileDto);
			}
			CustomerCarDto objCustomerCarDto = customerService.findCustomerCarById(id);
			if (objCustomerCarDto != null) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("Customer car đã tồn tại");
				responseDto.setError(errorDto);
				return responseDto;
			}
			// thuc hien tao customer car
			objCustomerCarDto = new CustomerCarDto();
			objCustomerCarDto.setNumberPlate(numberPlate);
			objCustomerCarDto.setId(id);
			objCustomerCarDto.setCustomer(cusId);
			objCustomerCarDto.setCreatedAt(DateTimeUtil.getCurrentDateTime());
			objCustomerCarDto.setUpdatedAt(DateTimeUtil.getCurrentDateTime());
			objCustomerCarDto.setStatus(status);
			objCustomerCarDto = customerService.saveCustomerCarEntity(objCustomerCarDto);
			responseDto.setError(errorDto);
			responseDto.setData(objCustomerCarDto);
			return responseDto;

		} catch (Exception ex) {
			LOGGER.error("Lỗi hệ thống: " + ex.getMessage());
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage(ex.getMessage());
			responseDto.setError(errorDto);
			return responseDto;
		}
	}

	/**
	 * liemnh Thuc hien goi de danh dau xoa customer car ben iparking
	 * 
	 * @param id
	 * @param cusId
	 * @return
	 */
	@RequestMapping(value = "/customer-car-delete", method = RequestMethod.POST)
	@ApiOperation("delete customer car")
	public @ResponseBody ResponseApi updateCustomerInfo(@RequestParam(name = "id") long id) {
		ResponseApi responseDto = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		try {
			// tim kiem theo id
			CustomerCarDto objCustomerCarDto = customerService.findCustomerCarById(id);
			if (objCustomerCarDto == null) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage(MessageUtil.MESSAGE_CUSTOMER_CAR_NOT_EXITS);
				responseDto.setError(errorDto);
				return responseDto;
			}
			objCustomerCarDto.setStatus(CustomerConstans.CUSTOMER_CAR_STATUS_DISABLE);
			objCustomerCarDto = customerService.saveCustomerCarEntity(objCustomerCarDto);
			responseDto.setData(objCustomerCarDto);
			return responseDto;
		} catch (Exception ex) {
			LOGGER.error("Lỗi hệ thống: " + ex.getMessage());
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage(ex.getMessage());
			responseDto.setError(errorDto);
			return responseDto;
		}
	}

	/**
	 * liemnh Thuc hien tao customer ben iparking
	 * 
	 * @param cusId
	 * @param email
	 * @param verificationCode
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/customer-create", method = RequestMethod.POST)
	@ApiOperation("Create or update customer info")
	public @ResponseBody ResponseApi createCustomer(@RequestParam(name = "phone") String phone,
			@RequestParam(name = "phone2") String phone2, @RequestParam(name = "password") String password,
			@RequestParam(name = "telco") String telco, @RequestParam(name = "status_reason") String status_reason,
			@RequestParam(name = "checksum") String checkSum, @RequestParam(name = "status") int status,
			@RequestParam(name = "id") long old_id) {
		ResponseApi responseDto = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		try {
			// kiem tra so dien thoai
			if (!Utils.validateVNPhoneNumber(phone)) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("Phone Malformed");
				responseDto.setError(errorDto);
				return responseDto;
			}

			CustomerDto objCustomerDto = customerService.findCustomerByOldId(old_id);
			if (objCustomerDto != null) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("Customer exits");
				responseDto.setError(errorDto);
				return responseDto;
			}

			objCustomerDto = customerService.findByPhone2(phone);
			if (objCustomerDto != null) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("Customer by phone exits");
				responseDto.setError(errorDto);
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
			responseDto.setError(errorDto);
			return responseDto;
		} catch (Exception ex) {
			LOGGER.error("Lỗi hệ thống: " + ex.getMessage());
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage(ex.getMessage());
			responseDto.setError(errorDto);
			return responseDto;
		}
	}

	/**
	 * liemnh thuc hien tao capcha
	 * 
	 * @param captchaID
	 * @return
	 */
	@RequestMapping(value = "/capcha", method = RequestMethod.GET)
	@ApiOperation("signup customer")
	public @ResponseBody Object getCapcha(@RequestParam(name = "captchaID", required = true) String captchaID) {
		ResponseDto responseDto = new ResponseDto();
		try {
			if (StringUtils.isEmpty(captchaID)) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage(MessageUtil.MESSAGE_CANNOT_CHECK_CAPTCHA);
				return responseDto;
			}
			InputStream result = customerService.getCapcha(captchaID);
			if (result == null) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage("Lỗi tạo capcha");
				return responseDto;
			}
			responseDto.setCode(HttpStatus.OK.toString());
			responseDto.setData(IOUtils.toByteArray(result));
			return responseDto;
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("Lỗi hệ thống: " + e.getMessage());
			responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
			return responseDto;
		}
	}

	/**
	 * liemnh thuc hien gui otp den khach hang
	 * 
	 * @param phone
	 * @param captcha
	 * @param captchaID
	 * @return
	 */
	@RequestMapping(value = "/otp/signup", method = RequestMethod.POST)
	@ApiOperation("signup customer")
	public @ResponseBody ResponseApi otpSignUp(@RequestParam(name = "phone") String phone,
			@RequestParam(name = "captcha") String captcha, @RequestParam(name = "captchaID") String captchaID) {
		ResponseApi responseDto = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		try {

			if (StringUtils.isEmpty(phone)) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage(MessageUtil.MESSAGE_PHONE_WRONG_FORMAT);
				responseDto.setError(errorDto);
				return responseDto;
			}
			if (!Utils.validateVNPhoneNumber(String.valueOf(phone))) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage(MessageUtil.MESSAGE_PHONE_WRONG_FORMAT);
				responseDto.setError(errorDto);
				return responseDto;
			}
			Map<String, Object> result = customerService.otpSignupCallGolang(phone, captcha, captchaID);
			if (result == null || !HttpStatus.OK.toString().equals(result.get("Code"))) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage((String) result.get("Message"));
				responseDto.setError(errorDto);
				return responseDto;
			}
			responseDto.setData(result.get("Ticket"));
			responseDto.setError(errorDto);
			return responseDto;
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("Lỗi hệ thống: " + e.getMessage());
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage(e.getMessage());
			responseDto.setError(errorDto);
			return responseDto;
		}
	}

	/**
	 * liemnh thuc hien verify otp cua khach hang nhap
	 * 
	 * @param phone
	 * @param otp
	 * @param ticket
	 * @return
	 */
	@RequestMapping(value = "/otp/verify/signup", method = RequestMethod.POST)
	@ApiOperation("signup customer")
	public @ResponseBody ResponseApi otpVerifySignUp(@RequestParam(name = "phone") String phone,
			@RequestParam(name = "otp") String otp, @RequestParam(name = "ticket") String ticket) {
		ResponseApi responseDto = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		try {

			if (StringUtils.isEmpty(phone)) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage(MessageUtil.MESSAGE_PHONE_WRONG_FORMAT);
				responseDto.setError(errorDto);
				return responseDto;

			}
			if (!Utils.validateVNPhoneNumber(String.valueOf(phone))) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage(MessageUtil.MESSAGE_PHONE_WRONG_FORMAT);
				responseDto.setError(errorDto);
				return responseDto;
			}
			if (phone.startsWith(Utils.phone_prefix)) {
				phone = phone.replaceFirst(Utils.phone_prefix, Utils.phone_prefix_84);
			}
			Map<String, Object> result = customerService.verifyOtpSignupCallGolang(phone, otp, ticket);
			if (result == null || !HttpStatus.OK.toString().equals(result.get("Code"))) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage((String) result.get("Message"));
				responseDto.setError(errorDto);
				return responseDto;
			}
			responseDto.setError(errorDto);
			return responseDto;
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("Lỗi hệ thống: " + e.getMessage());
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage(e.getMessage());
			responseDto.setError(errorDto);
			return responseDto;
		}
	}

	/**
	 * liemnh thuc hien dang ky customer va tao customer info neu co
	 * 
	 * @param phone
	 * @param email
	 * @param pasword
	 * @return
	 */
	@RequestMapping(value = "/nap/signup", method = RequestMethod.POST)
	@ApiOperation("signup customer")
	public @ResponseBody ResponseApi napSignUp(@RequestParam(name = "phone") String phone,
			@RequestParam(name = "email", required = false) String email,
			@RequestParam(name = "pasword", required = true) String pasword) {
		ResponseApi responseDto = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		try {

			if (StringUtils.isEmpty(phone)) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage(MessageUtil.MESSAGE_PHONE_WRONG_FORMAT);
				responseDto.setError(errorDto);
				return responseDto;
			}
			if (!Utils.validateVNPhoneNumber(String.valueOf(phone))) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage(MessageUtil.MESSAGE_PHONE_WRONG_FORMAT);
				responseDto.setError(errorDto);
				return responseDto;
			}
			if (phone.startsWith(Utils.phone_prefix)) {
				phone = phone.replaceFirst(Utils.phone_prefix, Utils.phone_prefix_84);
			}
			// kiem tra phone tren he thong
			CustomerDto objCustomerDto = customerService.findByPhone2(phone);
			if (objCustomerDto != null) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage(MessageUtil.MESSAGE_CUSTOMER_EXITS);
				responseDto.setError(errorDto);
				return responseDto;
			}
			// thuc hien tao pasword
			LOGGER.info("Password:" + pasword);
			String passwordEncrypt = PasswordGenerator.encryptPassword(pasword);
			LOGGER.info("passwordEncrypt:" + passwordEncrypt);

			Map<String, Object> result = customerService.napSignupCallGolang(phone, passwordEncrypt);
			if (result == null || !HttpStatus.OK.toString().equals(result.get("Code"))) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage((String) result.get("Message"));
				responseDto.setError(errorDto);
				return responseDto;
			}
			objCustomerDto = new CustomerDto();
			objCustomerDto.setPhone(phone);
			objCustomerDto.setPhone2(phone);
			objCustomerDto.setTelco((String) result.get("telco"));
			objCustomerDto.setPassword(passwordEncrypt.getBytes());
			long cusId = (long) result.get("cus_id");
			objCustomerDto.setOldId(cusId);
			objCustomerDto.setCheckSum((String) result.get("checksum"));
			objCustomerDto.setStatus(UserConstant.STATUS_ACTIVATED);
			objCustomerDto.setCreatedAt(DateTimeUtil.getCurrentDateTime());
			objCustomerDto.setUpdatedAt(DateTimeUtil.getCurrentDateTime());
			objCustomerDto.setVerifyPhone(UserConstant.STATUS_VERIFIED);
			objCustomerDto = customerService.saveCustomerInIparkingCenter(objCustomerDto);

			if (objCustomerDto != null && !StringUtils.isEmpty(email)) {
				// thuc hien kiem tra email de them moi customer_info
				CustomerInfoDto objCustomerInfoDto = new CustomerInfoDto();
				objCustomerInfoDto.setCusId(cusId);
				objCustomerInfoDto.setEmail(email);
				objCustomerInfoDto.setVerificationCode("");
				objCustomerInfoDto.setStatus(UserConstant.STATUS_NOT_VERIFIED);
				objCustomerInfoDto.setCreatedAt(DateTimeUtil.getCurrentDateTime());
				customerService.saveCustomerInfoEntity(objCustomerInfoDto);
				customerService.saveCustomerInfoInPoseidonDb(cusId, phone, email);
			}
			responseDto.setData(objCustomerDto);
			responseDto.setError(errorDto);
			return responseDto;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			LOGGER.error("Lỗi hệ thống: " + e.getMessage());
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage(e.getMessage());
			responseDto.setError(errorDto);
			return responseDto;
		}
	}

	/**
	 * liemnh customer dang nhap he thong
	 * 
	 * @param phone
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/nap/signin", method = RequestMethod.GET)
	@ApiOperation("signup customer app")
	public @ResponseBody Object napSignIn(@RequestParam(name = "phone") String phone,
			@RequestParam(name = "password") String password) {
		ResponseDto responseDto = new ResponseDto();
		try {

			if (StringUtils.isEmpty(phone)) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage(MessageUtil.MESSAGE_PHONE_WRONG_FORMAT);
				return responseDto;
			}
			if (!Utils.validateVNPhoneNumber(String.valueOf(phone))) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage(MessageUtil.MESSAGE_PHONE_WRONG_FORMAT);
				return responseDto;
			}
			if (phone.startsWith(Utils.phone_prefix)) {
				phone = phone.replaceFirst(Utils.phone_prefix, Utils.phone_prefix_84);
			}
			CustomerDto objCustomerDto = customerService.findByPhone2(phone);

			if (objCustomerDto == null) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage(MessageUtil.MESSAGE_CUSTOMER_NOT_EXITS);
				return responseDto;
			}
			// kiem tra pass
			if (!PasswordGenerator.verifyPassword(password, new String(objCustomerDto.getPassword()))) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage(MessageUtil.MESSAGE_CUSTOMER_WRONG_PASS);
				return responseDto;
			}
			// thuc hien tạo token
			responseDto.setCode(HttpStatus.OK.toString());
			responseDto.setData(TokenAuthenticationService
					.createTokenCustomer(String.valueOf(objCustomerDto.getOldId()), objCustomerDto.getPhone2()));
			return responseDto;
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("Lỗi hệ thống: " + e.getMessage());
			responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
			return responseDto;
		}
	}

	/**
	 * liemnh customer dang nhap he thong
	 * 
	 * @param phone
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/web/signin", method = RequestMethod.GET)
	@ApiOperation("signup customer web")
	public @ResponseBody Object webSignIn(@RequestParam(name = "phone") String phone,
			@RequestParam(name = "password") String password) {
		ResponseDto responseDto = new ResponseDto();
		try {

			if (StringUtils.isEmpty(phone)) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage(MessageUtil.MESSAGE_PHONE_WRONG_FORMAT);
				return responseDto;
			}
			if (!Utils.validateVNPhoneNumber(String.valueOf(phone))) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage(MessageUtil.MESSAGE_PHONE_WRONG_FORMAT);
				return responseDto;
			}
			if (phone.startsWith(Utils.phone_prefix)) {
				phone = phone.replaceFirst(Utils.phone_prefix, Utils.phone_prefix_84);
			}
			CustomerDto objCustomerDto = customerService.findByPhone2(phone);

			if (objCustomerDto == null) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage(MessageUtil.MESSAGE_CUSTOMER_NOT_EXITS);
				return responseDto;
			}
			// kiem tra pass
			if (!PasswordGenerator.verifyPassword(password, new String(objCustomerDto.getPassword()))) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage(MessageUtil.MESSAGE_CUSTOMER_WRONG_PASS);
				return responseDto;
			}
			// thuc hien tạo token
			responseDto.setCode(HttpStatus.OK.toString());

			UserInfo userInfo = new UserInfo();
			userInfo.setToken(TokenAuthenticationService.createTokenCustomer(String.valueOf(objCustomerDto.getOldId()),
					objCustomerDto.getPhone2()));
			List<MenuDto> menus = customerService.getMenuCustomerByRoleForWeb(CustomerConstans.CUSTOMER_ROLE_DEFAULT);
			userInfo.setMenus(menus);
			responseDto.setData(userInfo);
			return responseDto;
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("Lỗi hệ thống: " + e.getMessage());
			responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
			return responseDto;
		}
	}

	/**
	 * liemnh chi tiêt customer
	 * 
	 * @param captchaID
	 * @return
	 */
	@RequestMapping(value = "/getCustomerDetail", method = RequestMethod.GET)
	@ApiOperation("signup customer")
	public @ResponseBody ResponseApi getCustomerDetail(HttpServletRequest request) {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		try {
			String cusId = TokenAuthenticationService.getAuthenticationInfo(request);
			if (StringUtils.isEmpty(cusId)) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("Authentication faile!");
				responseApi.setError(errorDto);
				return responseApi;
			}
			CustomerDto objCustomerDto = customerService.findCustomerByOldId(Long.parseLong(cusId));
			CustomerInfoDto objCustomerInfoDto = customerService.findCustomerInfoByCusId(objCustomerDto.getOldId());
			Map<String, Object> map = new HashMap<>();
			map.put("id", objCustomerDto.getOldId());
			map.put("phone", objCustomerDto.getPhone());
			map.put("phone2", objCustomerDto.getPhone2());
			map.put("status", objCustomerDto.getStatus());
			map.put("email", objCustomerInfoDto == null ? null : objCustomerInfoDto.getEmail());
			responseApi.setError(errorDto);
			responseApi.setData(map);
			return responseApi;
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error(ex.getMessage());
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage(ex.getMessage());
			responseApi.setError(errorDto);
			return responseApi;
		}
	}

	@Value("classpath:email.html")
	Resource resourceFile;

	/**
	 * liemnh resendPassword
	 * 
	 * @param captchaID
	 * @return
	 */
	@RequestMapping(value = "/resendPassword", method = RequestMethod.POST)
	@ApiOperation("resendPassword")
	public @ResponseBody ResponseApi resendPassword(HttpServletRequest request,
			@RequestParam(name = "email") String email) {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		try {
			if (!Utils.validateEmail(email)) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage(MessageUtil.MESSAGE_EMAIL_WRONG_FORMAT);
				responseApi.setError(errorDto);
				return responseApi;
			}
			CustomerInfoDto objCustomerInfoDto = customerService.findCustomerInfoByEmail(email);
			if (objCustomerInfoDto == null) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage(MessageUtil.MESSAGE_CUSTOMER_NOT_EXITS);
				responseApi.setError(errorDto);
				return responseApi;
			}
			// thuc hien tao checksum va customer recovery
			Date curentDate = DateTimeUtil.getCurrentDateTime();
			Calendar cal = Calendar.getInstance();
			cal.setTime(curentDate);
			cal.add(Calendar.MINUTE, 15);
			Date expire = cal.getTime();
			BigInteger checkSum = createCheckSumHashString(String.valueOf(objCustomerInfoDto.getCusId()), expire);
			CustomerRecoveryDto dto = new CustomerRecoveryDto();
			dto.setCusId(objCustomerInfoDto.getCusId());
			dto.setCreatedAt(curentDate);
			dto.setUpdatedAt(curentDate);
			dto.setCheckSum(checkSum.longValue());
			dto.setExpire(expire);
			customerRecoveryService.save(dto);
			// gui email den KH
			InputStream in = resourceFile.getInputStream();
			BufferedReader br = null;
			StringBuilder sb = new StringBuilder();
			if (in != null) {
				br = new BufferedReader(new InputStreamReader(in));
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line).append("\n");
				}
			}

			SimpleDateFormat simple = new SimpleDateFormat("HHmmssddMMyyyy");

			String finalURL = URLConstants.URL_RESEND_PASSWORD;
			finalURL = finalURL.replace("{id}", String.valueOf(objCustomerInfoDto.getCusId()));
			finalURL = finalURL.replace("{checksum}", checkSum.toString());
			finalURL = finalURL.replace("{expire}", simple.format(expire));

			emailService.sendEmailResendPassword(CustomerConstans.CUSTOMER_TITLE_EMAIL_RESET_PASSWORD,
					sb.toString().replace("[LINK]", finalURL).replace("[ACCOUNT]", email), email);
			responseApi.setError(errorDto);
			return responseApi;
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error(ex.getMessage());
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage(ex.getMessage());
			responseApi.setError(errorDto);
			return responseApi;
		}
	}

	/**
	 * liemnh resendPassword
	 * 
	 * @param captchaID
	 * @return
	 */
	@RequestMapping(value = "/verifyResendPassword", method = RequestMethod.POST)
	@ApiOperation("resendPassword")
	public @ResponseBody ResponseApi verifyResendPassword(HttpServletRequest request,
			@RequestParam(name = "cus-id") String cusId, @RequestParam(name = "checksum") String checksum,
			@RequestParam(name = "expire") String expire) {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		try {
			// tim kiem thong tin recovery
			CustomerRecoveryDto dto = customerRecoveryService.find(Long.parseLong(cusId), Long.parseLong(checksum));
			if (dto == null) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage(MessageUtil.MESSAGE_URL_EXPIRE);
				responseApi.setError(errorDto);
				return responseApi;
			}
			// thuc hien kiem tra thoi gian
			if (dto.getExpire().before(DateTimeUtil.getCurrentDateTime())) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage(MessageUtil.MESSAGE_URL_EXPIRE);
				responseApi.setError(errorDto);
				return responseApi;
			}
			responseApi.setError(errorDto);
			return responseApi;
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error(ex.getMessage());
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage(ex.getMessage());
			responseApi.setError(errorDto);
			return responseApi;
		}
	}

	/**
	 * liemnh changePassword
	 * 
	 * @param captchaID
	 * @return
	 */
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	@ApiOperation("changePassword")
	public @ResponseBody ResponseApi changePassword(HttpServletRequest request,
			@RequestParam(name = "cus-id") long cusId, @RequestParam(name = "email") String email,
			@RequestParam(name = "new-password") String newPassword) {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		try {
			CustomerDto objCustomerDto = customerService.findCustomerByOldId(cusId);
			if (objCustomerDto == null) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage(MessageUtil.MESSAGE_CUSTOMER_NOT_EXITS);
				responseApi.setError(errorDto);
				return responseApi;
			}
			// thuc hien cap nhat pasword
			String passwordEncrypt = PasswordGenerator.encryptPassword(newPassword);
			// thuc hien goi sang shardDB cap nhat
			Map<String, Object> resut = customerService.updateCustomerInShardDb(String.valueOf(cusId), passwordEncrypt);
			if (resut == null || !HttpStatus.OK.toString().equals(resut.get("Code"))) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("Cập nhật mật khẩu thất bại");
				responseApi.setError(errorDto);
				return responseApi;
			}
			objCustomerDto.setPassword(passwordEncrypt.getBytes());
			objCustomerDto.setCheckSum((String) resut.get("Checksum"));
			objCustomerDto.setUpdatedAt(DateTimeUtil.getCurrentDateTime());
			customerService.saveCustomerInIparkingCenter(objCustomerDto);
			// gui email thông bao đổi thành công
			emailService.sendEmailChangePasswordSuccess(
					CustomerConstans.CUSTOMER_TITLE_EMAIL_THONGBAO_CHANGE_PASS_SUCCESS,
					CustomerConstans.CUSTOMER_CONTENT_EMAIL_THONGBAO_CHANGE_PASS_SUCCESS, "liemnh267@gmail.com");
			return responseApi;
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error(ex.getMessage());
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage(ex.getMessage());
			responseApi.setError(errorDto);
			return responseApi;
		}
	}

	private BigInteger createCheckSumHashString(String cusId, Date dateExpire) {
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			sb.append("secretkey1");
			sb.append(cusId);
			sb.append(simple.format(dateExpire));
			return MD5Util.encryptLong(sb.toString());
		} catch (NoSuchAlgorithmException e) {
			return null;
		}

	}

}

package net.cis.controller;

import java.util.ArrayList;
import java.util.List;

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
import net.cis.constants.ResponseErrorCodeConstants;
import net.cis.dto.CustomerCarDto;
import net.cis.dto.CustomerDto;
import net.cis.dto.CustomerInfoDto;
import net.cis.dto.ErrorDto;
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
				return responseDto;
			}
			List<Object> lstResult = new ArrayList<Object>();
			for (CustomerCarDto objCustomerCarDto : lstCustomerCarDto) {
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
				lstResult.add(dataObject);
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
	public @ResponseBody ResponseApi updateCustomerInfo(@RequestParam(name = "cus_id") long cusId,
			@RequestParam(name = "email") String email,
			@RequestParam(name = "verification_code", required = false) String verificationCode,
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
	 * liemnh
	 * 
	 * @param id
	 * @param cusId
	 * @param numberPlate
	 * @return
	 */
	@RequestMapping(value = "/customer-car-update", method = RequestMethod.POST)
	@ApiOperation("create or update customer car")
	public @ResponseBody ResponseApi updateCustomerInfo(@RequestParam(name = "id") long id,
			@RequestParam(name = "cus_id") long cusId, @RequestParam(name = "number_plate") String numberPlate) {
		ResponseApi responseDto = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		try {
			// tim kiem customer
			CustomerDto objCustomerDto = customerService.findCustomerByOldId(cusId);
			// tim kiem CustomerInfo from db
			if (objCustomerDto == null) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("Không tồn tại customer");
				responseDto.setError(errorDto);
				return responseDto;
			}
			CustomerCarDto objCustomerCarDto = customerService.findCustomerCarByNumberPlateAndCusId(numberPlate, cusId);
			if (objCustomerCarDto == null) {
				objCustomerCarDto = new CustomerCarDto();
				objCustomerCarDto.setCustomer(cusId);
				objCustomerCarDto.setNumberPlate(numberPlate);
				objCustomerCarDto.setCreatedAt(DateTimeUtil.getCurrentDateTime());
				objCustomerCarDto.setUpdatedAt(DateTimeUtil.getCurrentDateTime());
				objCustomerCarDto = customerService.saveCustomerCarEntity(objCustomerCarDto);
			}
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
	 * liemnh
	 * 
	 * @param id
	 * @param cusId
	 * @return
	 */
	@RequestMapping(value = "/customer-car-delete", method = RequestMethod.POST)
	@ApiOperation("delete customer car")
	public @ResponseBody ResponseApi updateCustomerInfo(@RequestParam(name = "id") long id,
			@RequestParam(name = "cus_id") long cusId) {
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
			if (objCustomerCarDto.getCustomer() == cusId) {
				customerService.deleteCustomerCar(id);
				responseDto.setError(errorDto);
				return responseDto;
			} else {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage(MessageUtil.MESSAGE_CUSTOMER_NOT_EXITS);
				responseDto.setError(errorDto);
				return responseDto;
			}
		} catch (Exception ex) {
			LOGGER.error("Lỗi hệ thống: " + ex.getMessage());
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage(ex.getMessage());
			responseDto.setError(errorDto);
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
	public @ResponseBody ResponseApi createCustomer(@RequestParam(name = "phone") String phone,
			@RequestParam(name = "telco") String telco, @RequestParam(name = "password") String password,
			@RequestParam(name = "status_reason") String status_reason,
			@RequestParam(name = "checksum") String checkSum, @RequestParam(name = "status") int status,
			@RequestParam(name = "old_id") long old_id) {
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

}

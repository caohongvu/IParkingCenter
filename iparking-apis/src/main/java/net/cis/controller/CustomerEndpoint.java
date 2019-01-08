package net.cis.controller;

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
import net.cis.common.util.Utils;
import net.cis.common.util.constant.UserConstant;
import net.cis.dto.CustomerCarDto;
import net.cis.dto.CustomerDto;
import net.cis.dto.CustomerInfoDto;
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

	@RequestMapping(value = "/find-by-numberplate", method = RequestMethod.GET)
	@ApiOperation("Fetch details of ticket")
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
			List<CustomerCarDto> lstCustomerCarDto = customerService.findCustomerCarByNumberPlate(numberPlate,
					UserConstant.STATUS_VERIFIED);
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
			return responseDto;
		}

	}

	@RequestMapping(value = "/customer-info-update", method = RequestMethod.POST)
	@ApiOperation("Fetch details of ticket")
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

}

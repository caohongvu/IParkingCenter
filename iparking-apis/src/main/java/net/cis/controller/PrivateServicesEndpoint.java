package net.cis.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.cis.common.util.DateTimeUtil;
import net.cis.constants.CustomerConstans;
import net.cis.constants.PrivateServiceConstans;
import net.cis.constants.ResponseErrorCodeConstants;
import net.cis.dto.ErrorDto;
import net.cis.dto.PrivateServicesDto;
import net.cis.dto.PrivateServicesParkingCusDto;
import net.cis.dto.PrivateServicesParkingDto;
import net.cis.dto.ResponseApi;
import net.cis.security.filter.TokenAuthenticationService;
import net.cis.service.PrivateService;

@RestController
@RequestMapping("/private-service")
@Api(value = "private-service Endpoint", description = "The URL to handle private-service endpoint")
public class PrivateServicesEndpoint {
	protected final Logger LOGGER = Logger.getLogger(getClass());

	@Autowired
	PrivateService privateService;

	/**
	 * liemnh Xoa dich vu của 1 customer
	 * 
	 * @param oldId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/private-services", method = RequestMethod.GET)
	@ApiOperation("Danh sách dịch vụ")
	public @ResponseBody ResponseApi getListPrivateService() {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		responseApi.setError(errorDto);
		try {
			// tim kiem dịch vụ cho customer
			List<PrivateServicesDto> lst = privateService.getPrivateServices();
			responseApi.setData(lst);
			return responseApi;
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage(ex.getMessage());
			responseApi.setError(errorDto);
			return responseApi;
		}
	}

	/**
	 * liemnh Tao moi 1 dich vụ cho diem dich vu
	 * 
	 * @param oldId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/private-service-parking/create", method = RequestMethod.POST)
	@ApiOperation("Thêm mới dịch vụ cho 1 điểm dịch vụ")
	public @ResponseBody ResponseApi createPrivateServiceParkings(@RequestParam(name = "parking-id") Long parkingId,
			@RequestParam(name = "service-id") Long serviceId,
			@RequestParam(name = "status", required = false) Integer status) {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		responseApi.setError(errorDto);
		try {
			// thuc hien tim kiem dich vu cua diem do
			PrivateServicesParkingDto dto = privateService.findPrivateServicesParking(parkingId, serviceId);
			if (dto != null) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("Dịch vụ của tại điểm dịch vụ đã được cấu hình");
				responseApi.setError(errorDto);
				return responseApi;
			}
			if (status == null) {
				status = PrivateServiceConstans.STATUS_DISABLE;
			}
			dto = new PrivateServicesParkingDto();
			dto.setParkingId(parkingId);
			dto.setCreatedAt(DateTimeUtil.getCurrentDateTime());
			dto.setUpdatedAt(DateTimeUtil.getCurrentDateTime());
			dto.setStatus(status);

			PrivateServicesDto privateServicesDto = new PrivateServicesDto();
			privateServicesDto.setId(serviceId);
			dto.setPrivateService(privateServicesDto);
			responseApi.setData(privateService.savePrivateServicesParking(dto));
			return responseApi;
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage(ex.getMessage());
			responseApi.setError(errorDto);
			return responseApi;
		}
	}

	/**
	 * liemnh Câp nhật 1 dich vụ cho diem dich vu
	 * 
	 * @param oldId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/private-service-parking/update", method = RequestMethod.POST)
	@ApiOperation("Cập nhật dịch vụ cho 1 điểm dịch vụ")
	public @ResponseBody ResponseApi updatePrivateServiceParkings(@RequestParam(name = "id") Long id,
			@RequestParam(name = "status", required = false) Integer status) {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		responseApi.setError(errorDto);
		try {
			// thuc hien tim kiem dich vu cua diem do
			PrivateServicesParkingDto dto = privateService.findPrivateServicesParking(id);
			if (dto == null) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("Dịch vụ của tại điểm dịch vụ chưa được cấu hình");
				responseApi.setError(errorDto);
				return responseApi;
			}
			if (status == null) {
				status = PrivateServiceConstans.STATUS_DISABLE;
			}

			dto.setUpdatedAt(DateTimeUtil.getCurrentDateTime());
			dto.setStatus(status);
			responseApi.setData(privateService.savePrivateServicesParking(dto));
			return responseApi;
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage(ex.getMessage());
			responseApi.setError(errorDto);
			return responseApi;
		}
	}

	/**
	 * liemnh Xóa 1 dich vụ cho diem dich vu
	 * 
	 * @param oldId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/private-service-parking/delete", method = RequestMethod.DELETE)
	@ApiOperation("Xóa dịch vụ cho 1 điểm dịch vụ")
	public @ResponseBody ResponseApi deletePrivateServiceParkings(@RequestParam(name = "id") Long id) {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		responseApi.setError(errorDto);
		try {
			// thuc hien tim kiem dich vu cua diem do
			PrivateServicesParkingDto dto = privateService.findPrivateServicesParking(id);
			if (dto == null) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("Dịch vụ của tại điểm dịch vụ chưa được cấu hình");
				responseApi.setError(errorDto);
				return responseApi;
			}
			dto.setUpdatedAt(DateTimeUtil.getCurrentDateTime());
			dto.setStatus(PrivateServiceConstans.STATUS_DISABLE);
			privateService.savePrivateServicesParking(dto);
			return responseApi;
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage(ex.getMessage());
			responseApi.setError(errorDto);
			return responseApi;
		}
	}

	/**
	 * liemnh Lấy danh sach dịch vụ tại 1 điểm đỗ
	 * 
	 * @param oldId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPrivateServiceParkings", method = RequestMethod.GET)
	@ApiOperation("Danh sách dịch vụ tại 1 điểm dịch vụ")
	public @ResponseBody ResponseApi getPrivateServiceParkings(@RequestParam(name = "parking-id") Long parkingId,
			@RequestParam(name = "status", required = false) Integer status) {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		responseApi.setError(errorDto);
		try {
			responseApi.setData(privateService.getPrivateServiceParkings(parkingId, status));
			return responseApi;
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage(ex.getMessage());
			responseApi.setError(errorDto);
			return responseApi;
		}
	}

	/**
	 * liemnh Lấy danh sach dịch vụ của 1 customer
	 * 
	 * @param oldId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPrivateServiceParkingCustomer", method = RequestMethod.GET)
	@ApiOperation("Danh sách dịch vụ của 1 customer")
	public @ResponseBody ResponseApi getPrivateServiceParkingCustomer(
			@RequestParam(name = "customer-id") Long customerId) {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		responseApi.setError(errorDto);
		try {
			responseApi.setData(privateService.getPrivateServicesParkingCus(customerId));
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
	 * liemnh Thêm mới 1 dịch vụ của customer
	 * 
	 * @param oldId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/private-service-customer/create", method = RequestMethod.POST)
	@ApiOperation("Thêm mới 1 dịch vụ của customer")
	public @ResponseBody ResponseApi createPrivateServiceParkingCustomer(
			@RequestParam(name = "customer-id") Long customerId, @RequestParam(name = "parking-id") Long parkingId,
			@RequestParam(name = "service-id") Long serviceId, @RequestParam(name = "info") String info) {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		responseApi.setError(errorDto);
		try {
			// kiem tra dich vụ tại điểm dịch vụ có tồn tại hay ko
			PrivateServicesParkingDto objPrivateServicesParkingDto = privateService
					.findPrivateServicesParking(parkingId, serviceId, CustomerConstans.CUSTOMER_SERVICE_ENABLE);
			if (objPrivateServicesParkingDto == null) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("Dịch vụ của tại điểm dịch vụ chưa được cấu hình");
				responseApi.setError(errorDto);
				return responseApi;
			}
			// thuc hien them moi dich vu cho customer
			PrivateServicesParkingCusDto dto = new PrivateServicesParkingCusDto();
			dto.setParkingServiceParkingId(objPrivateServicesParkingDto.getId());
			dto.setCusId(customerId);
			dto.setInfo(info);
			dto.setStatus(CustomerConstans.CUSTOMER_SERVICE_ENABLE);
			dto.setCreatedAt(DateTimeUtil.getCurrentDateTime());
			dto.setUpdatedAt(DateTimeUtil.getCurrentDateTime());
			dto = privateService.savePrivateServicesParkingCus(dto);
			responseApi.setData(dto);
			return responseApi;
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage(ex.getMessage());
			responseApi.setError(errorDto);
			return responseApi;
		}
	}

	/**
	 * liemnh Cập nhật 1 dịch vụ của customer
	 * 
	 * @param oldId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/private-service-customer/update", method = RequestMethod.POST)
	@ApiOperation("Cập nhật 1 dịch vụ của customer")
	public @ResponseBody ResponseApi updatePrivateServiceParkingCustomer(@RequestParam(name = "id") Long id,
			@RequestParam(name = "info", required = false) String info, @RequestParam(name = "status") Integer status) {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		responseApi.setError(errorDto);
		try {
			// kiem tra dich vụ tại điểm dịch vụ có tồn tại hay ko
			PrivateServicesParkingCusDto objPrivateServicesParkingDto = privateService
					.findPrivateServicesParkingCusById(id);
			if (objPrivateServicesParkingDto == null) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("Khách hàng chưa cài đặt dịch vụ tại điểm đỗ");
				responseApi.setError(errorDto);
				return responseApi;
			}
			// thuc hien them moi dich vu cho customer
			if (!StringUtils.isEmpty(info))
				objPrivateServicesParkingDto.setInfo(info);
			objPrivateServicesParkingDto.setStatus(status);
			objPrivateServicesParkingDto.setUpdatedAt(DateTimeUtil.getCurrentDateTime());
			objPrivateServicesParkingDto = privateService.savePrivateServicesParkingCus(objPrivateServicesParkingDto);
			responseApi.setData(objPrivateServicesParkingDto);
			return responseApi;
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage(ex.getMessage());
			responseApi.setError(errorDto);
			return responseApi;
		}
	}

	/**
	 * liemnh Xóa dịch vụ của 1 customer
	 * 
	 * @param oldId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/private-service-customer/delete", method = RequestMethod.DELETE)
	@ApiOperation("Xóa dịch vụ của 1 customer")
	public @ResponseBody ResponseApi deletePrivateServiceParkingCustomer(@RequestParam(name = "id") Long id) {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		responseApi.setError(errorDto);
		try {
			// tim kiem dịch vụ cho customer
			PrivateServicesParkingCusDto objPrivateServicesParkingCustomerDto = privateService
					.findPrivateServicesParkingCusById(id);
			if (objPrivateServicesParkingCustomerDto == null) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("Không tồn tại dịch vụ của customer");
				responseApi.setError(errorDto);
				return responseApi;
			}
			// thuc hien cap nhat dich vu của customer thanh disable
			objPrivateServicesParkingCustomerDto.setStatus(CustomerConstans.CUSTOMER_SERVICE_DISABLE);
			privateService.savePrivateServicesParkingCus(objPrivateServicesParkingCustomerDto);
			return responseApi;
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage(ex.getMessage());
			responseApi.setError(errorDto);
			return responseApi;
		}
	}

	/**
	 * liemnh Lấy thông tin thanh toán cần thanh toán của customer
	 * 
	 * @param oldId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/bill-customer", method = RequestMethod.GET)
	@ApiOperation("Lấy thông tin thanh toán cần thanh toán của customer")
	public @ResponseBody ResponseApi getBillPaymentByCustomer(HttpServletRequest request) {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		responseApi.setError(errorDto);
		try {
			String customerId = TokenAuthenticationService.getAuthenticationInfo(request);
			if (StringUtils.isEmpty(customerId)) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("Authentication faile!");
				responseApi.setError(errorDto);
				return responseApi;
			}
			responseApi.setData(privateService.getBillCusstomers(Long.parseLong(customerId),
					PrivateServiceConstans.BILL_STATUS_UNPAID));
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
}

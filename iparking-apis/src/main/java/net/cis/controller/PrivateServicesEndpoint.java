package net.cis.controller;

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
import net.cis.constants.ResponseErrorCodeConstants;
import net.cis.dto.ErrorDto;
import net.cis.dto.PrivateServicesParkingCusDto;
import net.cis.dto.PrivateServicesParkingDto;
import net.cis.dto.ResponseApi;
import net.cis.service.PrivateServicesService;

@RestController
@RequestMapping("/private-service")
@Api(value = "private-service Endpoint", description = "The URL to handle private-service endpoint")
public class PrivateServicesEndpoint {
	protected final Logger LOGGER = Logger.getLogger(getClass());

	@Autowired
	PrivateServicesService privateServicesService;

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
			@RequestParam(name = "status") Integer status) {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		responseApi.setError(errorDto);
		try {
			responseApi.setData(privateServicesService.getPrivateServiceParkings(parkingId, status));
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
			responseApi.setData(privateServicesService.getPrivateServicesParkingCus(customerId));
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
	 * liemnh Tạo 1 dịch vụ của customer
	 * 
	 * @param oldId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/private-service-customer/create", method = RequestMethod.POST)
	@ApiOperation("Tạo 1 dịch vụ của customer")
	public @ResponseBody ResponseApi createPrivateServiceParkingCustomer(
			@RequestParam(name = "customer-id") Long customerId, @RequestParam(name = "parking-id") Long parkingId,
			@RequestParam(name = "service-id") Long serviceId, @RequestParam(name = "info") String info) {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		responseApi.setError(errorDto);
		try {
			// kiem tra dich vụ tại điểm dịch vụ có tồn tại hay ko
			PrivateServicesParkingDto objPrivateServicesParkingDto = privateServicesService
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
			dto = privateServicesService.savePrivateServicesParkingCus(dto);
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
	 * liemnh Xoa dich vu của 1 customer
	 * 
	 * @param oldId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/private-service-customer/delete", method = RequestMethod.POST)
	@ApiOperation("Xoa dich vu của 1 customer")
	public @ResponseBody ResponseApi deletePrivateServiceParkingCustomer(@RequestParam(name = "id") Long id) {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		responseApi.setError(errorDto);
		try {
			// tim kiem dịch vụ cho customer
			PrivateServicesParkingCusDto objPrivateServicesParkingCustomerDto = privateServicesService
					.findPrivateServicesParkingCusById(id);
			if (objPrivateServicesParkingCustomerDto == null) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("Không tồn tại dịch vụ của customer");
				responseApi.setError(errorDto);
				return responseApi;
			}
			// thuc hien cap nhat dich vu của customer thanh disable
			objPrivateServicesParkingCustomerDto.setStatus(CustomerConstans.CUSTOMER_SERVICE_DISABLE);
			privateServicesService.savePrivateServicesParkingCus(objPrivateServicesParkingCustomerDto);
			return responseApi;
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage(ex.getMessage());
			responseApi.setError(errorDto);
			return responseApi;
		}
	}
}

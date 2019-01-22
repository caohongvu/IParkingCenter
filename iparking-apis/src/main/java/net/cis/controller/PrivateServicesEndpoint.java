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
import net.cis.constants.ResponseErrorCodeConstants;
import net.cis.dto.ErrorDto;
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
	 * Lấy danh sach dịch vụ tại 1 điểm đỗ
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
	 * Lấy danh sach dịch vụ tại của 1 customer
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
	 * Lấy danh sach dịch vụ tại của 1 customer
	 * 
	 * @param oldId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/private-service-customer/create", method = RequestMethod.POST)
	@ApiOperation("Danh sách dịch vụ của 1 customer")
	public @ResponseBody ResponseApi createPrivateServiceParkingCustomer(
			@RequestParam(name = "customer-id") Long customerId) {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		responseApi.setError(errorDto);
		try {
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
	 * Lấy danh sach dịch vụ tại của 1 customer
	 * 
	 * @param oldId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/private-service-customer/delete", method = RequestMethod.POST)
	@ApiOperation("Danh sách dịch vụ của 1 customer")
	public @ResponseBody ResponseApi deletePrivateServiceParkingCustomer(
			@RequestParam(name = "customer-id") Long customerId) {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		responseApi.setError(errorDto);
		try {
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

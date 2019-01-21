package net.cis.controller;

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
import net.cis.constants.ResponseErrorCodeConstants;
import net.cis.dto.ErrorDto;
import net.cis.dto.ParkingConfigDto;
import net.cis.dto.ParkingConfigTypeDto;
import net.cis.dto.ResponseApi;
import net.cis.service.ParkingConfigService;

/**
 * 
 * @author liemnh
 *
 */
@RestController
@RequestMapping("/parking-config")
@Api(value = "Parking config Endpoint", description = "The URL to handle Parking config endpoint")
public class ParkingConfigEndPoint {
	protected final Logger LOGGER = Logger.getLogger(getClass());
	@Autowired
	ParkingConfigService parkingConfigService;

	@RequestMapping(value = "/find-parking-config-type", method = RequestMethod.GET)
	@ApiOperation("Lay danh sach kieu cau hinh")
	public @ResponseBody ResponseApi getParkingConfigTypes(HttpServletRequest request,
			@RequestParam(name = "status") Integer status, @RequestParam(name = "name", required = false) String name) {
		ResponseApi responseDto = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		try {

			if (StringUtils.isEmpty(name)) {
				responseDto.setData(parkingConfigService.getParkingConfigTypes(status));
				responseDto.setError(errorDto);
				return responseDto;
			}
			responseDto.setData(parkingConfigService.getParkingConfigTypes(status, name));
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

	@RequestMapping(value = "/create-parking-config-type", method = RequestMethod.POST)
	@ApiOperation("Tạo mới kiểu cấu hình")
	public @ResponseBody ResponseApi createParkingConfigType(HttpServletRequest request,
			@RequestParam(name = "name") String name,
			@RequestParam(name = "description", required = false) String description,
			@RequestParam(name = "status") Integer status) {
		ResponseApi responseDto = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		try {

			ParkingConfigTypeDto objParkingConfigTypeDto = new ParkingConfigTypeDto();
			objParkingConfigTypeDto.setName(name);
			objParkingConfigTypeDto.setDescription(description);
			objParkingConfigTypeDto.setStatus(status);
			objParkingConfigTypeDto.setCreatedAt(DateTimeUtil.getCurrentDateTime());
			objParkingConfigTypeDto.setUpdatedAt(DateTimeUtil.getCurrentDateTime());
			responseDto.setData(parkingConfigService.saveParkingConfigType(objParkingConfigTypeDto));
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

	@RequestMapping(value = "/update-parking-config-type", method = RequestMethod.POST)
	@ApiOperation("Cập nhật kiểu cấu hình")
	public @ResponseBody ResponseApi updateParkingConfigType(HttpServletRequest request,
			@RequestParam(name = "id") Long id, @RequestParam(name = "name") String name,
			@RequestParam(name = "description", required = false) String description,
			@RequestParam(name = "status") Integer status) {
		ResponseApi responseDto = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		try {
			ParkingConfigTypeDto dto = parkingConfigService.findParkingConfigTypeById(id);
			if (dto == null) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("ParkingConfigType không tồn tại");
				responseDto.setError(errorDto);
				return responseDto;
			}
			dto.setName(name);
			dto.setDescription(description);
			dto.setStatus(status);
			responseDto.setData(parkingConfigService.saveParkingConfigType(dto));
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

	@RequestMapping(value = "/find-parking-config", method = RequestMethod.GET)
	@ApiOperation("Lấy danh sách cấu hình")
	public @ResponseBody ResponseApi getParkingConfigs(HttpServletRequest request,
			@RequestParam(name = "config-key", required = false) String configKey,
			@RequestParam(name = "company", required = false) Long company,
			@RequestParam(name = "config-type", required = false) Long configType) {
		ResponseApi responseDto = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		try {
			responseDto.setData(parkingConfigService.getParkingConfig(configKey, company, configType));
			responseDto.setError(errorDto);
			return responseDto;

		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error("Lỗi hệ thống: " + ex.getMessage());
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage(ex.getMessage());
			responseDto.setError(errorDto);
			return responseDto;
		}
	}

	@RequestMapping(value = "/create-parking-config", method = RequestMethod.POST)
	@ApiOperation("Tao parking config ")
	public @ResponseBody ResponseApi createParkingConfig(HttpServletRequest request,
			@RequestParam(name = "config_key") String configKey,
			@RequestParam(name = "config_value") String configValue,
			@RequestParam(name = "config_description", required = false) String configDescription,
			@RequestParam(name = "company", required = false) Long company,
			@RequestParam(name = "parking-config-type") Long configType) {
		ResponseApi responseDto = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		try {

			ParkingConfigDto objParkingConfigDto = new ParkingConfigDto();
			objParkingConfigDto.setConfigKey(configKey);
			objParkingConfigDto.setConfigValue(configValue);
			objParkingConfigDto.setConfigDescription(configDescription);
			objParkingConfigDto.setCompanyId(company);
			objParkingConfigDto.setParkingConfigTypeId(configType);
			objParkingConfigDto.setCreatedAt(DateTimeUtil.getCurrentDateTime());
			objParkingConfigDto.setUpdatedAt(DateTimeUtil.getCurrentDateTime());
			responseDto.setData(parkingConfigService.saveParkingConfig(objParkingConfigDto));
			responseDto.setError(errorDto);
			return responseDto;

		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error("Lỗi hệ thống: " + ex.getMessage());
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage(ex.getMessage());
			responseDto.setError(errorDto);
			return responseDto;
		}
	}

	@RequestMapping(value = "/update-parking-config", method = RequestMethod.POST)
	@ApiOperation("Cap nhat parking config")
	public @ResponseBody ResponseApi updateParkingConfig(HttpServletRequest request, @RequestParam(name = "id") Long id,
			@RequestParam(name = "config_value") String configValue) {
		ResponseApi responseDto = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		try {
			ParkingConfigDto objParkingConfigDto = parkingConfigService.findParkingConfigById(id);
			if (objParkingConfigDto == null) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("ParkingConfig không tồn tại");
				responseDto.setError(errorDto);
				return responseDto;
			}
			objParkingConfigDto.setConfigValue(configValue);
			objParkingConfigDto.setUpdatedAt(DateTimeUtil.getCurrentDateTime());
			responseDto.setData(parkingConfigService.saveParkingConfig(objParkingConfigDto));
			responseDto.setError(errorDto);
			return responseDto;
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error("Lỗi hệ thống: " + ex.getMessage());
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage(ex.getMessage());
			responseDto.setError(errorDto);
			return responseDto;
		}
	}
}

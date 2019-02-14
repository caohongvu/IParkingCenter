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

	/**
	 * liemnh tim kiem parking config type
	 * 
	 * @param request
	 * @param status
	 * @param name
	 * @return
	 */
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

	/**
	 * liemnh tim kiem parking config
	 * 
	 * @param request
	 * @param configKey
	 * @param company
	 * @param configType
	 * @return
	 */
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

	/**
	 * liemnh update parking config
	 * 
	 * @param request
	 * @param id
	 * @param configValue
	 * @return
	 */
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

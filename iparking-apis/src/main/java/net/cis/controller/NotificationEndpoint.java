package net.cis.controller;

import java.util.List;

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
import net.cis.constants.ResponseErrorCodeConstants;
import net.cis.dto.ErrorDto;
import net.cis.dto.NotificationDto;
import net.cis.dto.ParkingDto;
import net.cis.dto.ResponseApi;
import net.cis.security.filter.TokenAuthenticationService;
import net.cis.service.NotificationService;
import net.cis.service.ParkingContractService;
import net.cis.service.ParkingService;

@RestController
@RequestMapping("/notification")
@Api(value = "notification Endpoint", description = "The URL to handle notification endpoint")
public class NotificationEndpoint {
	protected final Logger LOGGER = Logger.getLogger(getClass());

	@Autowired
	ParkingContractService parkingContractService;

	@Autowired
	NotificationService notificationHistoryService;

	@Autowired
	ParkingService parkingService;

	/**
	 * Lay thong tin khach hang cua ve thang cua 1 diem do
	 * 
	 * @param request
	 * @param parkingCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/push", method = RequestMethod.POST)
	@ApiOperation("push (Notification, Email, Sms)")
	public @ResponseBody ResponseApi getCompany(HttpServletRequest request,
			@RequestParam(name = "parking_code") String parkingCode, @RequestParam(name = "title") String title,
			@RequestParam(name = "content") String content, @RequestParam(name = "type") List<Integer> type)
			throws Exception {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		String supervisorId = TokenAuthenticationService.getAuthenticationInfo(request);
		try {
			if (StringUtils.isEmpty(supervisorId)) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("Authentication faile!");
				responseApi.setError(errorDto);
				return responseApi;
			}
			if (StringUtils.isEmpty(parkingCode)) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("Parking Code empty");
				responseApi.setError(errorDto);
				return responseApi;
			}
			if (StringUtils.isEmpty(title)) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("Title empty");
				responseApi.setError(errorDto);
				return responseApi;
			}
			if (StringUtils.isEmpty(content)) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("Content empty");
				responseApi.setError(errorDto);
				return responseApi;
			}
			notificationHistoryService.push(parkingCode, title, content, supervisorId, type);
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

	@RequestMapping(value = "/getNotificationHistory", method = RequestMethod.GET)
	@ApiOperation("Nhat ky gui notification")
	public @ResponseBody ResponseApi getCompany(HttpServletRequest request,
			@RequestParam(name = "parking_code") String parkingCode) throws Exception {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		try {
			String supervisorId = TokenAuthenticationService.getAuthenticationInfo(request);
			if (StringUtils.isEmpty(supervisorId)) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("Authentication faile!");
				responseApi.setError(errorDto);
				return responseApi;
			}

			ParkingDto objParkingDto = parkingService.findByParkingCode(parkingCode);
			if (objParkingDto == null) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("Parking does not exits");
				responseApi.setError(errorDto);
				return responseApi;
			}
			List<NotificationDto> lstNotificationHistoryDto = notificationHistoryService
					.findAllByCreatedBy(Long.parseLong(objParkingDto.getOldId()), Long.parseLong(supervisorId));
			responseApi.setData(lstNotificationHistoryDto);
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
}

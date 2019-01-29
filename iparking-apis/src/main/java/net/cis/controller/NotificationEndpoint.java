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
import net.cis.dto.ParkingActorDto;
import net.cis.dto.ParkingDto;
import net.cis.dto.ResponseApi;
import net.cis.security.filter.TokenAuthenticationService;
import net.cis.service.NotificationService;
import net.cis.service.ParkingActorService;
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

	@Autowired
	ParkingActorService parkingActorService;

	/**
	 * liemnh Thuc hien gui thong bao den customer cua 1 diem dich vu
	 * 
	 * @param request
	 * @param parkingCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/push", method = RequestMethod.POST)
	@ApiOperation("push (Notification, Email, Sms)")
	public @ResponseBody ResponseApi getCompany(HttpServletRequest request, @RequestParam(name = "title") String title,
			@RequestParam(name = "content", required = false) String content,
			@RequestParam(name = "content_sms", required = false) String contentSms,
			@RequestParam(name = "type") List<Integer> type) {
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
			if (StringUtils.isEmpty(title)) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("Title empty");
				responseApi.setError(errorDto);
				return responseApi;
			}
			// lấy thông tin cppCode mà user quản l
			List<ParkingActorDto> parkingActorDtos = parkingActorService.findByActors(Long.parseLong(supervisorId));
			if (parkingActorDtos == null || parkingActorDtos.size() == 0) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("User chưa được cấu hình điểm dịch vụ");
				responseApi.setError(errorDto);
				return responseApi;
			}

			// thuc hien lay thông tin cpp code
			ParkingDto objParkingDto = parkingService.findByOldId(String.valueOf(parkingActorDtos.get(0).getCppId()));
			if (objParkingDto == null) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("Điểm dịch vụ không tồn tại");
				responseApi.setError(errorDto);
				return responseApi;
			}
			notificationHistoryService.push(objParkingDto, title, content, contentSms, supervisorId, type);
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
	 * liemnh lay lich su gui thong bao
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getNotificationHistory", method = RequestMethod.GET)
	@ApiOperation("Nhat ky gui notification")
	public @ResponseBody ResponseApi getNotificationHistory(HttpServletRequest request) {
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
			// lấy thông tin cppCode mà user quản ly
			List<ParkingActorDto> parkingActorDtos = parkingActorService.findByActors(Long.parseLong(supervisorId));
			if (parkingActorDtos == null || parkingActorDtos.size() == 0) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("User chưa được cấu hình điểm dịch vụ");
				responseApi.setError(errorDto);
				return responseApi;
			}
			List<NotificationDto> lstNotificationHistoryDto = notificationHistoryService
					.findAllByCreatedBy(parkingActorDtos.get(0).getCppId(), Long.parseLong(supervisorId));
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

	/**
	 * liemnh lay lich su gui thong bao
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getNotificationCustomerHistory", method = RequestMethod.GET)
	@ApiOperation("Nhat ky gui notification")
	public @ResponseBody ResponseApi getNotificationCustomerHistory(HttpServletRequest request) {
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
			// lay thong tin notification cua customer
			List<NotificationDto> lstResult = notificationHistoryService
					.findNotificationCustomer(Long.parseLong(cusId));
			responseApi.setError(errorDto);
			responseApi.setData(lstResult);
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

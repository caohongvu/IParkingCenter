package net.cis.controller;

import java.util.ArrayList;
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
import net.cis.common.util.DateTimeUtil;
import net.cis.constants.NotificationType;
import net.cis.constants.ResponseErrorCodeConstants;
import net.cis.dto.ErrorDto;
import net.cis.dto.NotificationHistoryDto;
import net.cis.dto.ParkingContractInfoDto;
import net.cis.dto.ResponseApi;
import net.cis.jpa.criteria.ParkingContractCriteria;
import net.cis.security.filter.TokenAuthenticationService;
import net.cis.service.EmailService;
import net.cis.service.NotificationHistoryService;
import net.cis.service.ParkingContractService;
import net.cis.service.SmsService;

@RestController
@RequestMapping("/notification")
@Api(value = "notification Endpoint", description = "The URL to handle notification endpoint")
public class NotificationEndpoint {
	protected final Logger LOGGER = Logger.getLogger(getClass());

	@Autowired
	ParkingContractService parkingContractService;

	@Autowired
	NotificationHistoryService notificationHistoryService;

	@Autowired
	SmsService smsService;

	@Autowired
	EmailService emailService;

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
			@RequestParam(name = "content") String content, @RequestParam(name = "type") int type) throws Exception {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		String supervisorId = TokenAuthenticationService.getAuthenticationInfo(request);
		try {
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
			ParkingContractCriteria ticketCriteria = new ParkingContractCriteria();
			ticketCriteria.setCppCode(parkingCode);
			// lay danh sach khach hang ve thang theo diem do
			List<ParkingContractInfoDto> lstParkingContractInfoDto = parkingContractService
					.findParkingContractInfo(ticketCriteria);
			if (lstParkingContractInfoDto == null || lstParkingContractInfoDto.size() <= 0) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
				errorDto.setMessage("Data empty");
				responseApi.setError(errorDto);
				return responseApi;
			}
			List<String> lstDevices = new ArrayList<>();
			List<String> lstPhone = new ArrayList<>();
			List<String> lstEmail = new ArrayList<>();
			for (ParkingContractInfoDto objParkingContractInfoDto : lstParkingContractInfoDto) {
				if (!StringUtils.isEmpty(objParkingContractInfoDto.getEmail())) {
					lstEmail.add(objParkingContractInfoDto.getEmail());
				}
				if (!StringUtils.isEmpty(objParkingContractInfoDto.getPhone2())) {
					lstPhone.add(objParkingContractInfoDto.getPhone2());
				}
			}
			responseApi.setData(lstParkingContractInfoDto);

			// thuc hien luu history
			NotificationHistoryDto objNotificationHistoryDto = new NotificationHistoryDto();
			objNotificationHistoryDto.setTitle(title);
			objNotificationHistoryDto.setContent(content);
			objNotificationHistoryDto.setCreatedBy(Long.parseLong(supervisorId));
			objNotificationHistoryDto.setCreatedAt(DateTimeUtil.getCurrentDateTime());
			objNotificationHistoryDto.setType(type);

			if (NotificationType.NOTIFICATION == type) {
				// notification
				objNotificationHistoryDto.setSended(lstDevices.toString());
			} else if (NotificationType.EMAIL == type) {
				// email
				emailService.sendASynchronousMail(title, content, lstEmail.toArray(new String[lstEmail.size()]));
				objNotificationHistoryDto.setSended(lstEmail.toString());
			} else {
				// sms
				for (String phoneSend : lstPhone) {
					smsService.sendSms(phoneSend, content);
				}
				objNotificationHistoryDto.setSended(lstPhone.toString());
			}
			notificationHistoryService.save(objNotificationHistoryDto);
			return responseApi;
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage(ex.getMessage());
			responseApi.setError(errorDto);
			return responseApi;
		}
	}

	@RequestMapping(value = "/getNotificationHistory", method = RequestMethod.GET)
	@ApiOperation("Nhat ky gui notification")
	public @ResponseBody ResponseApi getCompany(HttpServletRequest request) throws Exception {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		try {
			String companyId = TokenAuthenticationService.getAuthenticationInfo(request);
			if (StringUtils.isEmpty(companyId)) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				responseApi.setError(errorDto);
				return responseApi;
			}
			List<NotificationHistoryDto> lstNotificationHistoryDto = notificationHistoryService
					.findAllByCreatedBy(Long.parseLong(companyId));
			responseApi.setData(lstNotificationHistoryDto);
			responseApi.setError(errorDto);
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

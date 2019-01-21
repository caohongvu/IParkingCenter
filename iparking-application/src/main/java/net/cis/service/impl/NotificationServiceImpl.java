package net.cis.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cis.common.util.DateTimeUtil;
import net.cis.constants.NotificationType;
import net.cis.dto.NotificationCustomerDto;
import net.cis.dto.NotificationDto;
import net.cis.dto.NotificationParkingPlaceDto;
import net.cis.dto.ParkingContractInfoDto;
import net.cis.dto.ParkingDto;
import net.cis.dto.UserDto;
import net.cis.jpa.criteria.ParkingContractCriteria;
import net.cis.jpa.entity.NotificationCustomerEntity;
import net.cis.jpa.entity.NotificationEntity;
import net.cis.jpa.entity.NotificationParkingPlaceEntity;
import net.cis.repository.NotificationCustomerRepository;
import net.cis.repository.NotificationParkingPlaceRepository;
import net.cis.repository.NotificationRepository;
import net.cis.service.EmailService;
import net.cis.service.NotificationService;
import net.cis.service.ParkingContractService;
import net.cis.service.ParkingService;
import net.cis.service.SmsService;
import net.cis.service.UserService;

@Service
public class NotificationServiceImpl implements NotificationService {
	@Autowired
	NotificationRepository notificationRepository;

	@Autowired
	NotificationParkingPlaceRepository notificationParkingPlaceRepository;

	@Autowired
	NotificationCustomerRepository notificationCustomerRepository;

	@Autowired
	ParkingContractService parkingContractService;

	@Autowired
	UserService userService;

	@Autowired
	SmsService smsService;

	@Autowired
	EmailService emailService;

	@Autowired
	ParkingService parkingService;

	ModelMapper mapper;

	@PostConstruct
	public void initialize() {
		mapper = new ModelMapper();
	}

	@Override
	public NotificationDto saveNotification(NotificationDto notificationHistoryDto) {
		NotificationEntity entity = new NotificationEntity();
		mapper.map(notificationHistoryDto, entity);
		mapper.map(notificationRepository.save(entity), notificationHistoryDto);
		return notificationHistoryDto;
	}

	@Override
	public List<NotificationDto> findAllByCreatedBy(long parkingId, long createdBy) {
		List<NotificationEntity> ticketEntities = notificationRepository.findByCreatedBy(createdBy);
		List<NotificationDto> ticketDtos = this.map(ticketEntities, parkingId, createdBy);
		return ticketDtos;
	}

	public List<NotificationDto> map(List<NotificationEntity> source, long parkingCode, long createdBy) {
		UserDto objUserDto = userService.findUserById((int) createdBy);
		ArrayList<NotificationDto> rtn = new ArrayList<>();
		source.stream().map((entity) -> {
			NotificationDto dto = new NotificationDto();
			mapper.map(entity, dto);
			dto.setCreatedByFullName(objUserDto.getFullname());
			dto.setCreatedByUserName(objUserDto.getUsername());
			List<NotificationParkingPlaceEntity> lstNotificationParkingPlace = findNotificationParkingPlace(dto.getId(),
					parkingCode);
			List<Integer> lstTypes = new ArrayList<>();
			for (NotificationParkingPlaceEntity type : lstNotificationParkingPlace) {
				lstTypes.add(type.getType());
			}
			dto.setTypes(lstTypes);
			return dto;
		}).forEachOrdered((dto) -> {
			rtn.add(dto);
		});
		return rtn;
	}

	public List<NotificationDto> map(List<NotificationEntity> source) {
		ArrayList<NotificationDto> rtn = new ArrayList<>();
		source.stream().map((entity) -> {
			NotificationDto dto = new NotificationDto();
			mapper.map(entity, dto);
			return dto;
		}).forEachOrdered((dto) -> {
			rtn.add(dto);
		});
		return rtn;
	}

	@Override
	public void push(String parkingCode, String title, String content,  String contentSms, String createdBy, List<Integer> types)
			throws Exception {
		ParkingDto objParkingDto = parkingService.findByParkingCode(parkingCode);
		if (objParkingDto == null) {
			throw new Exception("Parking does not exits");
		}

		ParkingContractCriteria ticketCriteria = new ParkingContractCriteria();
		ticketCriteria.setCppCode(parkingCode);
		// lay danh sach khach hang ve thang theo diem do
		List<ParkingContractInfoDto> lstParkingContractInfoDto = parkingContractService
				.findParkingContractInfo(ticketCriteria);
		List<String> lstDevices = new ArrayList<>();
		List<String> lstPhone = new ArrayList<>();
		List<String> lstEmail = new ArrayList<>();
		// thuc hien luu history Notification
		NotificationDto objNotificationHistoryDto = new NotificationDto();
		objNotificationHistoryDto.setTitle(title);
		objNotificationHistoryDto.setContent(content);
		objNotificationHistoryDto.setContentSms(contentSms);
		objNotificationHistoryDto.setCreatedBy(Long.parseLong(createdBy));
		objNotificationHistoryDto.setCreatedAt(DateTimeUtil.getCurrentDateTime());
		objNotificationHistoryDto = saveNotification(objNotificationHistoryDto);

		for (ParkingContractInfoDto objParkingContractInfoDto : lstParkingContractInfoDto) {
			// thuc hien luu danh sach customer nhan tin
			NotificationCustomerDto objNotificationCustomerDto = new NotificationCustomerDto();
			objNotificationCustomerDto.setNotificationId(objNotificationHistoryDto.getId());
			objNotificationCustomerDto.setCusId(objParkingContractInfoDto.getCusId());
			objNotificationCustomerDto.setCreatedAt(DateTimeUtil.getCurrentDateTime());
			saveNotificationCustomer(objNotificationCustomerDto);

			if (!StringUtils.isEmpty(objParkingContractInfoDto.getEmail())) {
				lstEmail.add(objParkingContractInfoDto.getEmail());
			}
			if (!StringUtils.isEmpty(objParkingContractInfoDto.getPhone2())) {
				lstPhone.add(objParkingContractInfoDto.getPhone2());
			}
		}

		for (Integer type : types) {
			// thuc hien save notification_parking_place
			NotificationParkingPlaceDto objNotificationCpp = new NotificationParkingPlaceDto();
			objNotificationCpp.setNotificationId(objNotificationHistoryDto.getId());
			objNotificationCpp.setParkingId(Long.parseLong(objParkingDto.getOldId()));
			objNotificationCpp.setCompanyId(objParkingDto.getCompany());
			objNotificationCpp.setType(type);
			objNotificationCpp.setCreatedAt(DateTimeUtil.getCurrentDateTime());
			saveNotificationParkingPlace(objNotificationCpp);
			if (NotificationType.NOTIFICATION == type) {
				// notification

			} else if (NotificationType.EMAIL == type) {
				// email
				emailService.sendASynchronousMail(title, content, lstEmail.toArray(new String[lstEmail.size()]));
			} else {
				// sms
				for (String phoneSend : lstPhone) {
					smsService.sendSms(phoneSend, content);
				}
			}
		}

	}

	@Override
	public NotificationParkingPlaceDto saveNotificationParkingPlace(
			NotificationParkingPlaceDto notificationParkingPlaceDto) {
		NotificationParkingPlaceEntity entity = new NotificationParkingPlaceEntity();
		mapper.map(notificationParkingPlaceDto, entity);
		mapper.map(notificationParkingPlaceRepository.save(entity), notificationParkingPlaceDto);
		return notificationParkingPlaceDto;
	}

	@Override
	public List<NotificationParkingPlaceEntity> findNotificationParkingPlace(long notificationId, long parkingId) {
		List<NotificationParkingPlaceEntity> lstResult = notificationParkingPlaceRepository
				.findByNotificationIdAndParkingId(notificationId, parkingId);
		return lstResult;
	}

	@Override
	public NotificationCustomerDto saveNotificationCustomer(NotificationCustomerDto notificationCustomerDto) {
		NotificationCustomerEntity entity = new NotificationCustomerEntity();
		mapper.map(notificationCustomerDto, entity);
		mapper.map(notificationCustomerRepository.save(entity), notificationCustomerDto);
		return notificationCustomerDto;
	}

}

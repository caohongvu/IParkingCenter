package net.cis.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cis.common.util.DateTimeUtil;
import net.cis.constants.CustomerConstans;
import net.cis.constants.NotificationType;
import net.cis.constants.NotificationTypeEnum;
import net.cis.dto.CustomerNotificationDto;
import net.cis.dto.NotificationCustomerDto;
import net.cis.dto.NotificationDto;
import net.cis.dto.NotificationParkingPlaceDto;
import net.cis.dto.NotificationTypeDto;
import net.cis.dto.ParkingContractInfoDto;
import net.cis.dto.ParkingDto;
import net.cis.dto.UserDto;
import net.cis.jpa.criteria.ParkingContractCriteria;
import net.cis.jpa.entity.NotificationCustomerEntity;
import net.cis.jpa.entity.NotificationEntity;
import net.cis.jpa.entity.NotificationParkingPlaceEntity;
import net.cis.jpa.entity.NotificationTypeEntity;
import net.cis.repository.NotificationCustomerRepository;
import net.cis.repository.NotificationParkingPlaceRepository;
import net.cis.repository.NotificationRepository;
import net.cis.repository.NotificationTypeRepository;
import net.cis.service.CustomerService;
import net.cis.service.EmailService;
import net.cis.service.NotificationService;
import net.cis.service.ParkingContractService;
import net.cis.service.ParkingService;
import net.cis.service.PushNotificationService;
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
	NotificationTypeRepository notificationTypeRepository;

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

	@Autowired
	private PushNotificationService pushNotificationService;

	@Autowired
	CustomerService customerService;
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
			NotificationParkingPlaceEntity notificationParkingPlaceEntity = notificationParkingPlaceRepository
					.findByNotificationIdAndParkingId(dto.getId(), parkingCode);
			List<NotificationTypeEntity> lstNotiTypes = notificationTypeRepository.findByNotificationId(dto.getId());
			List<Integer> lstTypes = new ArrayList<>();
			for (NotificationTypeEntity notificationTypeEntity : lstNotiTypes) {
				lstTypes.add(notificationTypeEntity.getType());
			}
			dto.setTypes(lstTypes);
			dto.setParkingId(notificationParkingPlaceEntity.getParkingId());
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
	public void push(ParkingDto objParkingDto, String title, String content, String contentSms, String createdBy,
			List<Integer> types) throws Exception {
		ParkingContractCriteria ticketCriteria = new ParkingContractCriteria();
		ticketCriteria.setCppCode(objParkingDto.getParkingCode());
		// lay danh sach khach hang ve thang theo diem do
		List<ParkingContractInfoDto> lstParkingContractInfoDto = parkingContractService
				.findParkingContractInfo(ticketCriteria);
		List<String> lstDeviceId = new ArrayList<>();
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
			if (!StringUtils.isEmpty(objParkingContractInfoDto.getToken())) {
				lstDeviceId.add(objParkingContractInfoDto.getToken().split(";")[0]);
			}
		}

		// thuc hien tao NotificationParkingPlace
		NotificationParkingPlaceDto objNotificationCpp = new NotificationParkingPlaceDto();
		objNotificationCpp.setNotificationId(objNotificationHistoryDto.getId());
		objNotificationCpp.setParkingId(Long.parseLong(objParkingDto.getOldId()));
		objNotificationCpp.setCompanyId(objParkingDto.getCompany());
		objNotificationCpp.setCreatedAt(DateTimeUtil.getCurrentDateTime());
		saveNotificationParkingPlace(objNotificationCpp);

		for (Integer type : types) {
			// thuc hien save notification_type
			NotificationTypeDto objNotificationTypeDto = new NotificationTypeDto();
			objNotificationTypeDto.setNotificationId(objNotificationHistoryDto.getId());
			objNotificationTypeDto.setType(type);
			objNotificationTypeDto.setCreatedAt(DateTimeUtil.getCurrentDateTime());
			saveNotificationType(objNotificationTypeDto);
			if (NotificationType.NOTIFICATION == type) {
				// notification
				pushNotificationService.sendNotificationForPlayerIds(lstDeviceId, NotificationTypeEnum.OTHER, title,
						content);
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
	public NotificationTypeDto saveNotificationType(NotificationTypeDto notificationTypeDto) {
		NotificationTypeEntity entity = new NotificationTypeEntity();
		mapper.map(notificationTypeDto, entity);
		mapper.map(notificationTypeRepository.save(entity), notificationTypeDto);
		return notificationTypeDto;
	}

	@Override
	public NotificationCustomerDto saveNotificationCustomer(NotificationCustomerDto notificationCustomerDto) {
		NotificationCustomerEntity entity = new NotificationCustomerEntity();
		mapper.map(notificationCustomerDto, entity);
		mapper.map(notificationCustomerRepository.save(entity), notificationCustomerDto);
		return notificationCustomerDto;
	}

	@Override
	public List<NotificationDto> findNotificationCustomer(long cusId) {
		List<NotificationCustomerEntity> lstEntity = notificationCustomerRepository.findByCusId(cusId);
		return this.mapNotificationCustomer(lstEntity);
	}

	public List<NotificationDto> mapNotificationCustomer(List<NotificationCustomerEntity> source) {
		List<NotificationDto> rtn = new ArrayList<>();
		source.stream().map((entity) -> {
			NotificationDto dto = new NotificationDto();
			// isRead
			dto.setIsRead(entity.getIsRead());
			// lay thong tin notfication
			NotificationEntity notification = notificationRepository.findOne(entity.getNotificationId());
			mapper.map(notification, dto);
			NotificationParkingPlaceEntity notificationParkingPlaceEntity = notificationParkingPlaceRepository
					.findByNotificationId(dto.getId());
			List<NotificationTypeEntity> lstNotiTypes = notificationTypeRepository.findByNotificationId(dto.getId());
			List<Integer> lstTypes = new ArrayList<>();
			for (NotificationTypeEntity notificationTypeEntity : lstNotiTypes) {
				lstTypes.add(notificationTypeEntity.getType());
			}
			dto.setTypes(lstTypes);
			dto.setParkingId(notificationParkingPlaceEntity.getParkingId());

			return dto;
		}).forEachOrdered((dto) -> {
			rtn.add(dto);
		});
		return rtn;
	}

	@Override
	public NotificationCustomerDto findNotificationCustomer(Long cusId, Long notificationId) {
		NotificationCustomerEntity entity = notificationCustomerRepository.findByCusIdAndNotificationId(cusId,
				notificationId);
		if (entity == null)
			return null;
		NotificationCustomerDto notificationCustomerDto = new NotificationCustomerDto();
		mapper.map(entity, notificationCustomerDto);
		return notificationCustomerDto;
	}

	@Override
	public void pushNotificationToCustomer(String title, String content, String createdBy, Long cusId)
			throws Exception {
		// thuc hien tim kiem devive cuar cusId
		List<CustomerNotificationDto> lstCustomerNotificationDto = customerService
				.findCustomerNotificationByCusId(cusId, CustomerConstans.CUSTOMER_NOTIFICATION_SUBSCRICE);
		if (lstCustomerNotificationDto == null || lstCustomerNotificationDto.size() == 0) {
			throw new Exception("Không tồn tại device của customer");
		}
		List<String> playerIds = new ArrayList<>();

		for (CustomerNotificationDto dto : lstCustomerNotificationDto) {
			playerIds.add(dto.getToken().split(";")[0]);
		}
		// thuc hien luu history Notification
		NotificationDto objNotificationHistoryDto = new NotificationDto();
		objNotificationHistoryDto.setTitle(title);
		objNotificationHistoryDto.setContent(content);
		objNotificationHistoryDto.setCreatedBy(Long.parseLong(createdBy));
		objNotificationHistoryDto.setCreatedAt(DateTimeUtil.getCurrentDateTime());
		objNotificationHistoryDto = saveNotification(objNotificationHistoryDto);
		// thuc hien luu danh sach customer nhan tin
		NotificationCustomerDto objNotificationCustomerDto = new NotificationCustomerDto();
		objNotificationCustomerDto.setNotificationId(objNotificationHistoryDto.getId());
		objNotificationCustomerDto.setCusId(cusId);
		objNotificationCustomerDto.setCreatedAt(DateTimeUtil.getCurrentDateTime());
		objNotificationCustomerDto.setIsRead(CustomerConstans.CUSTOMER_NOTIFICATION_UN_READ);
		saveNotificationCustomer(objNotificationCustomerDto);
		// thuc hien save notification_type
		NotificationTypeDto objNotificationTypeDto = new NotificationTypeDto();
		objNotificationTypeDto.setNotificationId(objNotificationHistoryDto.getId());
		objNotificationTypeDto.setType(NotificationType.NOTIFICATION);
		objNotificationTypeDto.setCreatedAt(DateTimeUtil.getCurrentDateTime());
		saveNotificationType(objNotificationTypeDto);
		pushNotificationService.sendNotificationForPlayerIds(playerIds, NotificationTypeEnum.OTHER, title, content);
	}

}

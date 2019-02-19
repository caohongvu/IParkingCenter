package net.cis.service;

import java.util.List;

import net.cis.dto.NotificationCustomerDto;
import net.cis.dto.NotificationDto;
import net.cis.dto.NotificationParkingPlaceDto;
import net.cis.dto.NotificationTypeDto;
import net.cis.dto.ParkingDto;

public interface NotificationService {

	void push(ParkingDto objParkingDto, String title, String content, String contentSms, String createdBy,
			List<Integer> type) throws Exception;

	NotificationDto saveNotification(NotificationDto notificationHistoryDto);

	NotificationParkingPlaceDto saveNotificationParkingPlace(NotificationParkingPlaceDto notificationParkingPlaceDto);

	NotificationCustomerDto saveNotificationCustomer(NotificationCustomerDto notificationCustomerDto);

	List<NotificationDto> findAllByCreatedBy(long parkingId, long createdBy);

	List<NotificationDto> findNotificationCustomer(long cusId);

	NotificationTypeDto saveNotificationType(NotificationTypeDto notificationTypeDto);

	NotificationCustomerDto findNotificationCustomer(Long cusId, Long notificationId);

	void pushNotificationToCustomer(String title, String content, String createdBy, Long cusId) throws Exception;

	NotificationDto findNotification(Long cusId, Long notificationId);

}

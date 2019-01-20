package net.cis.service;

import java.util.List;

import net.cis.dto.NotificationCustomerDto;
import net.cis.dto.NotificationDto;
import net.cis.dto.NotificationParkingPlaceDto;
import net.cis.jpa.entity.NotificationParkingPlaceEntity;

public interface NotificationService {

	void push(String parkingCode, String title, String content, String createdBy, List<Integer> type) throws Exception;

	NotificationDto saveNotification(NotificationDto notificationHistoryDto);

	NotificationParkingPlaceDto saveNotificationParkingPlace(NotificationParkingPlaceDto notificationParkingPlaceDto);

	NotificationCustomerDto saveNotificationCustomer(NotificationCustomerDto notificationCustomerDto);

	List<NotificationDto> findAllByCreatedBy(long parkingId, long createdBy);

	List<NotificationParkingPlaceEntity> findNotificationParkingPlace(long parkingId, long notificationId);
}

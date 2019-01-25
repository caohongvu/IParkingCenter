package net.cis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.NotificationParkingPlaceEntity;

public interface NotificationParkingPlaceRepository extends JpaRepository<NotificationParkingPlaceEntity, Long> {

	NotificationParkingPlaceEntity findByNotificationIdAndParkingId(long notificationId, long parkingId);

	NotificationParkingPlaceEntity findByNotificationId(long notificationId);
}

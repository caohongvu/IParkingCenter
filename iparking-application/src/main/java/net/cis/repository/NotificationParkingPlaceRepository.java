package net.cis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.NotificationParkingPlaceEntity;

public interface NotificationParkingPlaceRepository extends JpaRepository<NotificationParkingPlaceEntity, Long> {

	List<NotificationParkingPlaceEntity> findByNotificationIdAndParkingId(long notificationId, long parkingId);
}

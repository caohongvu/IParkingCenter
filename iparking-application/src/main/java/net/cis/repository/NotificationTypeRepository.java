package net.cis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.NotificationTypeEntity;

public interface NotificationTypeRepository extends JpaRepository<NotificationTypeEntity, Long> {
	List<NotificationTypeEntity> findByNotificationId(long notificationId);
}

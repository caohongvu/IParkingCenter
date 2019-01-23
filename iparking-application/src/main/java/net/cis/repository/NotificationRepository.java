package net.cis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.NotificationEntity;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

	List<NotificationEntity> findByCreatedBy(Long createdBy);  

}

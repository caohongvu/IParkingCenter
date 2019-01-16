package net.cis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.NotificationHistoryEntity;

public interface NotificationHistoryRepository extends JpaRepository<NotificationHistoryEntity, Long> {

	List<NotificationHistoryEntity> findByCreatedBy(long createdBy);  

}

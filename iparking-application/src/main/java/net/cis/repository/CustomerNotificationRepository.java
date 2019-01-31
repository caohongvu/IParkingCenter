package net.cis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.CustomerNotificationEntity;

public interface CustomerNotificationRepository extends JpaRepository<CustomerNotificationEntity, Long> {
	List<CustomerNotificationEntity> findByCusIdAndSubscrice(long cusId, Integer subscrice);
}

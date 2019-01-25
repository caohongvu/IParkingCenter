package net.cis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.NotificationCustomerEntity;

public interface NotificationCustomerRepository extends JpaRepository<NotificationCustomerEntity, Long> {
	List<NotificationCustomerEntity> findByCusId(long cusId);
}

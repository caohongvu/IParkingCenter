package net.cis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.ReportDelegatePaymentEntity;

public interface ReportDelegatePaymentRepository extends JpaRepository<ReportDelegatePaymentEntity, Long> {
	List<ReportDelegatePaymentEntity> findByCarppIdInAndCreatedDate(List<Long> carppIds, String date);
	
}

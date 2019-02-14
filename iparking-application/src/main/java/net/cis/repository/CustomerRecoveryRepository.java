package net.cis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.CustomerRecoveryEntity;

public interface CustomerRecoveryRepository extends JpaRepository<CustomerRecoveryEntity, Long> {
	CustomerRecoveryEntity findByCusIdAndCheckSum(Long cusId, String checkSum);
}

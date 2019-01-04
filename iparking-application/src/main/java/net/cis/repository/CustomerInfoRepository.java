package net.cis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.CustomerInfoEntity;

public interface CustomerInfoRepository extends JpaRepository<CustomerInfoEntity, Long> {
	CustomerInfoEntity findByCusId(long cusId);
}

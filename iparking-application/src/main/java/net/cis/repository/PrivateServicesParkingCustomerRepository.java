package net.cis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.PrivateServicesParkingCustomerEntity;

public interface PrivateServicesParkingCustomerRepository
		extends JpaRepository<PrivateServicesParkingCustomerEntity, Long> {

	List<PrivateServicesParkingCustomerEntity> findByCusIdAndStatus(Long cusId, Integer status);

}

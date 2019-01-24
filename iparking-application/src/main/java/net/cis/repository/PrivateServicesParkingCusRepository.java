package net.cis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.PrivateServicesParkingCusEntity;

public interface PrivateServicesParkingCusRepository
		extends JpaRepository<PrivateServicesParkingCusEntity, Long> {

	List<PrivateServicesParkingCusEntity> findByCusIdAndStatus(Long cusId, Integer status);

}

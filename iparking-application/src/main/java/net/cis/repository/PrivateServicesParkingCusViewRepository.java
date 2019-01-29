package net.cis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.PrivateServicesParkingCusViewEntity;

public interface PrivateServicesParkingCusViewRepository
		extends JpaRepository<PrivateServicesParkingCusViewEntity, Long> {
	List<PrivateServicesParkingCusViewEntity> findByCusId(Long cusId);
}

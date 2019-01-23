package net.cis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.PrivateServicesParkingEntity;

public interface PrivateServicesParkingRepository extends JpaRepository<PrivateServicesParkingEntity, Long> {
	List<PrivateServicesParkingEntity> findByParkingIdAndStatus(Long parkingId, Integer status);

	List<PrivateServicesParkingEntity> findByParkingId(Long parkingId);
}

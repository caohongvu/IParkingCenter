package net.cis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.ParkingInfoEntity;

public interface ParkingInfoRepository extends JpaRepository<ParkingInfoEntity, Long> {
	ParkingInfoEntity findByCarppId(long carppId);
}

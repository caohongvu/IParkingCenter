package net.cis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.ParkingPlaceHistoryEntity;

public interface ParkingPlaceHistoryRepository extends JpaRepository<ParkingPlaceHistoryEntity, Integer>{
	public List<ParkingPlaceHistoryEntity> findByOldId(String oldId);
}

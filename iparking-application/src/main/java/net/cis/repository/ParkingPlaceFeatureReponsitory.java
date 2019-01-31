package net.cis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.ParkingPlaceFeatureEntity;

public interface ParkingPlaceFeatureReponsitory extends JpaRepository<ParkingPlaceFeatureEntity,Integer>{
	public List<ParkingPlaceFeatureEntity> findByOldId(String oldId);


}

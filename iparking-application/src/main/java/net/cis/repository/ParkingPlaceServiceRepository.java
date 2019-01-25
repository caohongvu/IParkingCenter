package net.cis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.ParkingPlaceServiceEntity;

public interface ParkingPlaceServiceRepository extends JpaRepository<ParkingPlaceServiceEntity, Integer> { 

}

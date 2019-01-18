package net.cis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.ParkingConfigTypeEntity;

public interface ParkingConfigTypeRepository extends JpaRepository<ParkingConfigTypeEntity, Long> {

}

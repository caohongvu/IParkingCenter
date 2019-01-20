package net.cis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.ParkingConfigTypeEntity;

public interface ParkingConfigTypeRepository extends JpaRepository<ParkingConfigTypeEntity, Long> {
	List<ParkingConfigTypeEntity> findByStatus(Integer status);

	List<ParkingConfigTypeEntity> findByStatusAndNameIgnoreCaseContaining(Integer status, String name);
}

package net.cis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.ParkingConfigEntity;

public interface ParkingConfigRepository extends JpaRepository<ParkingConfigEntity, Long> {
	ParkingConfigEntity findByConfigKey(String configKey);

	List<ParkingConfigEntity> findByParkingConfigTypeId(long configKey);
}

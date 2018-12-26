package net.cis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.ParkingConfig;

public interface ParkingConfigRepository extends JpaRepository<ParkingConfig, Long> {
	ParkingConfig findByConfigKey(String configKey);
}

package net.cis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.ParkingContractEntity;

/**
 * Created by Vincent 02/10/2018
 */

public interface ParkingContractRepository  extends JpaRepository<ParkingContractEntity, Long> {
	
	
}

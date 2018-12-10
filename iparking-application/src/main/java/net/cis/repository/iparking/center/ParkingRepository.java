package net.cis.repository.iparking.center;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.ParkingEntity;

/**
 * Created by Vincent 02/10/2018
 */

public interface ParkingRepository  extends JpaRepository<ParkingEntity, Long> {
	
}

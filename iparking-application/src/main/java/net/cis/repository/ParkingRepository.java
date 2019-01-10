package net.cis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.cis.jpa.entity.ParkingEntity;

/**
 * Created by Vincent 02/10/2018
 */

public interface ParkingRepository extends JpaRepository<ParkingEntity, Long> {
	@Query("SELECT parking FROM ParkingEntity parking WHERE parking.oldId =:oldId")
	public ParkingEntity findByOldId(@Param("oldId") String oldId);

	public ParkingEntity findByParkingCode(String parkingCode);
}

package net.cis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.cis.jpa.entity.ParkingEntity;

/**
 * Created by Vincent 02/10/2018
 */

public interface ParkingRepository extends JpaRepository<ParkingEntity, Long> {
	@Query("SELECT parking FROM ParkingEntity parking WHERE parking.oldId =:oldId")
	public ParkingEntity findByOldId(@Param("oldId") long oldId);

	public ParkingEntity findByParkingCodeIgnoreCase(String parkingCode);

	public List<ParkingEntity> findByCompany(long company);

	List<ParkingEntity> findByOldIdIn(List<Long> oldIds);
}

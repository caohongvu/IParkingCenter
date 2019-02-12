package net.cis.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.cis.jpa.entity.ParkingContractOutOfDateFooterEntity;

public interface ParkingContractOutOfDateFooterRepository extends JpaRepository<ParkingContractOutOfDateFooterEntity, Long> {

	@Query(value = "Select ticket from ParkingContractOutOfDateFooterEntity ticket where (parkingPlace =:cppCode OR :cppCode is NULL)")
	public ParkingContractOutOfDateFooterEntity findParkingContractOutOfDate(@Param("cppCode") String cppCode);
}

package net.cis.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.cis.jpa.entity.ParkingContractOutOfDateEntity;

public interface ParkingContractOutOfDateRepository extends JpaRepository<ParkingContractOutOfDateEntity, Long> {
	@Query(value = "Select ticket from ParkingContractOutOfDateEntity ticket where (parkingPlace =:cppCode OR :cppCode is NULL)")
	public List<ParkingContractOutOfDateEntity> findParkingContractOutOfDate(@Param("cppCode") String cppCode,
			Pageable pageable);
}

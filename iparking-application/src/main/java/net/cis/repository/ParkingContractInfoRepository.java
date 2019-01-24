package net.cis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.cis.jpa.entity.ParkingContractInfoEntity;

public interface ParkingContractInfoRepository extends JpaRepository<ParkingContractInfoEntity, Long> {

	@Query(value = "Select ticket from ParkingContractInfoEntity ticket where (parkingPlace =:cppCode OR :cppCode is NULL)")
	public List<ParkingContractInfoEntity> findParkingContractInfo(@Param("cppCode") String cppCode);
}

package net.cis.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.cis.jpa.entity.MonthlyTicketEntity;

/**
 * Created by Vincent 02/10/2018
 */

public interface MonthlyTicketRepository  extends JpaRepository<MonthlyTicketEntity, Long> {
	
	@Query(value = "Select ticket from MonthlyTicketEntity ticket where (parkingPlace =:cppCode OR :cppCode is NULL) AND (UNIX_TIMESTAMP() >= validFrom AND UNIX_TIMESTAMP() <= validEnd ) order by createdAt DESC")
	public List<MonthlyTicketEntity> findAll(@Param("cppCode") String cppCode, Pageable pageable);
}

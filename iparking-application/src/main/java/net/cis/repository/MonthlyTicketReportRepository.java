package net.cis.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.cis.jpa.entity.MonthlyTicketReportEntity;

public interface MonthlyTicketReportRepository extends JpaRepository<MonthlyTicketReportEntity, String> {
	
	@Query("SELECT ticket FROM MonthlyTicketReportEntity ticket WHERE "
			+ "(ticket.parking_place LIKE CONCAT('%',:parking_place,'%') OR :parking_place is NULL) "
			+ "AND NOT (ticket.valid_end < :fromDate OR :toDate < ticket.valid_from) "
			+ "AND ((:status = 4 AND ticket.status <> 0) "
			+ "OR (:status = 3 AND ticket.is_liquidated = true AND ticket.status <> 0 AND ticket.status <> 2) "
			+ "OR (ticket.status = :status AND ((ticket.status = 2 AND  ticket.is_liquidated = true) "
			+ "OR ticket.is_liquidated = false) ) ) "
			+ "AND ((:is_paid = 1 AND ticket.is_paid = 0) OR "
			+ "(:is_paid = 2 AND ticket.is_paid = 1 AND ticket.valid_to < ticket.valid_end) OR "
			+ "(:is_paid = 3 AND ticket.is_paid = 1 AND ticket.valid_to = ticket.valid_end) OR (:is_paid = 0) ) "
			+ "AND ((:expired =1 AND (TIMESTAMPDIFF(DAY, FROM_UNIXTIME(ticket.valid_to), NOW()) <= 30)) OR "
			+ "(:expired =2 AND (TIMESTAMPDIFF(DAY,  FROM_UNIXTIME(ticket.valid_to), NOW()) > 30) AND  ticket.valid_to < ticket.valid_end ) OR (:expired = :expired) ) "
			+ "AND (ticket.number_plate LIKE CONCAT('%',:number_plate,'%') OR :number_plate is NULL) "
			+ "AND (ticket.contract_code LIKE CONCAT('%',:contract_code,'%') OR :contract_code is NULL) "
			+ "AND (ticket.contract_no LIKE CONCAT('%',:contract_no,'%') OR :contract_no is NULL) "
			+ "AND (ticket.phone LIKE CONCAT('%',:phone,'%') OR :phone is NULL) "
			+ "AND (ticket.fullName LIKE CONCAT('%',:fullName,'%') OR :fullName is NULL) "
			+ "GROUP BY ticket.ticket_id ")
	public List<MonthlyTicketReportEntity> findAllReport(@Param("is_paid") int is_paid,@Param("parking_place") String parking_place,
			@Param("expired") int expired,@Param("phone") Long phone,  
			@Param("number_plate") String number_plate, @Param("fullName") String fullName,
			@Param("contract_code") String contract_code, @Param("contract_no") String contract_no,
			@Param("status") int status,
			@Param("fromDate") Long fromDate, @Param("toDate") Long toDate,
			Pageable pageable);

}

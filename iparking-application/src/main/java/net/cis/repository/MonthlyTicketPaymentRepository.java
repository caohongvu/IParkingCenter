package net.cis.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.cis.jpa.entity.MonthlyTicketPaymentEntity;

public interface MonthlyTicketPaymentRepository extends JpaRepository<MonthlyTicketPaymentEntity, String> {
	
	@Query("SELECT ticket FROM MonthlyTicketPaymentEntity ticket WHERE (ticket.transaction_id LIKE CONCAT('%',:transID,'%') "
			+ "OR :transID is NULL) "
			+ "AND (ticket.parking_place LIKE CONCAT('%',:cpp_code,'%') OR :cpp_code is NULL) "
			+ "AND (ticket.contract_code LIKE CONCAT('%',:contract_code,'%') OR :contract_code is NULL) "
			+ "AND (ticket.contract_no LIKE CONCAT('%',:contract_no,'%') OR :contract_no is NULL) "
			+ "AND (ticket.phone LIKE CONCAT('%',:phone,'%') OR :phone is NULL) "
			+ "AND (ticket.card_number LIKE CONCAT('%',:card_number,'%') OR :card_number is NULL) "
			+ "AND (ticket.number_plate LIKE CONCAT('%',:number_plate,'%') OR :number_plate is NULL) "
			+ "AND ticket.created_at >=:fromTime AND ticket.created_at <=:toTime "
			+ "AND ((MONTH(ticket.apply_from_time) <=:periodMonth AND  MONTH(ticket.apply_to_time) >=:periodMonth "
			+ "AND YEAR(ticket.apply_from_time) <=:periodYear AND :periodYear <= YEAR(ticket.apply_to_time)) "
			+ "OR (:periodMonth is 0 AND :periodYear is 0)) "
			+ "GROUP BY ticket.transaction_id "
			+ "ORDER BY ticket.created_at")
	public List<MonthlyTicketPaymentEntity> findAll( @Param("transID") String transID, @Param("cpp_code") String cpp_code,
			@Param("contract_no") String contract_no, @Param("contract_code") String contract_code,
			@Param("number_plate") String numberplate, @Param("phone") Long phone,
			@Param("card_number") String card_number, @Param("fromTime") String from_time, 
			@Param("toTime") String to_Time, @Param("periodMonth") int periodMonth, @Param("periodYear") int periodYear, 
			Pageable pageable);
}

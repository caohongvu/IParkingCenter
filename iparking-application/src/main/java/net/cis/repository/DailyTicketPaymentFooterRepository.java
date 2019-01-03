package net.cis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.cis.jpa.entity.DailyTicketPaymentFooterEntity;

public interface DailyTicketPaymentFooterRepository extends JpaRepository<DailyTicketPaymentFooterEntity, Long>{
	@Query("SELECT ticket FROM DailyTicketPaymentFooterEntity ticket WHERE (ticket.transaction_id LIKE CONCAT('%',:transID,'%') OR :transID is NULL) AND (ticket.payment_trans_no LIKE CONCAT('%',:orderID,'%') OR :orderID is NULL) AND ticket.start_time >=:start_time AND ticket.start_time <=:end_time AND (ticket.parking_code LIKE CONCAT('%',:cppCode,'%') OR :cppCode is NULL) AND (ticket.car_number_plate LIKE CONCAT('%',:numberplate,'%') OR :numberplate is NULL) AND (ticket.phone LIKE CONCAT('%',:phone,'%') OR :phone is NULL) AND (ticket.card_number LIKE CONCAT('%',:cardNumber,'%') OR :cardNumber is NULL) AND (ticket.transaction_type =:transType OR :transType is NULL) order by ticket.created_at DESC")
	public DailyTicketPaymentFooterEntity findAllFooter(@Param("orderID") String orderID, 
			@Param("transID") String transID, @Param("cppCode") String cppCode,
			@Param("numberplate") String numberplate, @Param("phone") Long phone, 
			@Param("start_time") String start_time, @Param("end_time") String end_time, @Param("cardNumber") String cardNumber,
			@Param("transType") String transType);
}

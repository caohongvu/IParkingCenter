package net.cis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.cis.jpa.entity.TicketDailyPortalFooterEntity;


public interface TicketDailyPortalFooterRepository extends JpaRepository<TicketDailyPortalFooterEntity,Long>{
	@Query("SELECT ticket FROM TicketDailyPortalFooterEntity ticket " 
			+ "WHERE ticket.start_time >=:start_time "
			+ "AND ticket.start_time <=:end_time "
			+ "AND (ticket.parking_code LIKE CONCAT('%',:parking_code,'%') OR :parking_code is NULL) "
			+ "AND (ticket.car_number_plate LIKE CONCAT('%',:car_number_plate,'%') OR :car_number_plate is NULL) "
			+ "AND (ticket.phone2 LIKE CONCAT('%',:phone2,'%') OR :phone2 is NULL) order by ticket.start_time DESC")
	public TicketDailyPortalFooterEntity getAllTicketDailyFooterPortal(@Param("parking_code") String parking_code,
			@Param("car_number_plate") String car_number_plate, @Param("phone2") Long phone2,
			@Param("start_time") String start_time, @Param("end_time") String end_time);
}

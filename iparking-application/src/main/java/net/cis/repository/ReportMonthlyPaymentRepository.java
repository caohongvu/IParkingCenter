package net.cis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.cis.jpa.entity.ReportMonthlyPaymentEntity;

public interface ReportMonthlyPaymentRepository extends JpaRepository<ReportMonthlyPaymentEntity, Long> {
	@Query(value = "Select ticket from ReportMonthlyPaymentEntity ticket where (parkingId in :parkingId) AND ( day >= :fromDate AND day <= :toDate ) GROUP BY day")
	List<ReportMonthlyPaymentEntity> getSummaryReportMonthlyPaymentEntity(@Param("parkingId") List<Long> cppId,
			@Param("fromDate") Long fromDate, @Param("toDate") Long toDate);
}

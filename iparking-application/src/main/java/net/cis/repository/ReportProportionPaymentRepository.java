package net.cis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.cis.dto.ReportProportionPaymentDto;
import net.cis.jpa.entity.ReportProportionPaymentEntity;

public interface ReportProportionPaymentRepository extends JpaRepository<ReportProportionPaymentEntity, String> {
	List<ReportProportionPaymentEntity> findByDayGreaterThanEqualAndDayLessThanEqual(String fromDate, String toDate);

	@Query(value = "Select new net.cis.dto.ReportProportionPaymentDto(rpp.id as id,rpp.parkingCode as parkingCode, rpp.company as company ,sum(rpp.revenue) as revenue,sum(rpp.sms) as sms, sum(rpp.atm) as atm, sum(rpp.balance) as balance, sum(rpp.balance_tth) as balance_tth, sum(rpp.visa_master_kh) as visa_master_kh, sum(rpp.visa_master_tth) as visa_master_tth,sum(rpp.cash) as cash ,rpp.parkingId as parkingId ,rpp.companyId as companyId, cpp.address as address , cpp.capacity as capacity ) from ReportProportionPaymentEntity rpp INNER JOIN ParkingEntity cpp ON rpp.parkingId = cpp.oldId  where (rpp.parkingId in :cppId) AND ( rpp.day >= :fromDate AND rpp.day <= :toDate ) GROUP BY rpp.parkingCode")
	List<ReportProportionPaymentDto> findByProportionPaymentEntity(@Param("cppId") List<Long> cppId,
			@Param("fromDate") long fromDate, @Param("toDate") long toDate);

	@Query(value = "Select ticket from ReportProportionPaymentEntity ticket where (parkingId =:cppId) AND ( day >= :fromDate AND day <= :toDate ) GROUP BY parkingCode")
	ReportProportionPaymentEntity findByProportionPaymentEntity(@Param("cppId") Long cppId,
			@Param("fromDate") long fromDate, @Param("toDate") long toDate);

}

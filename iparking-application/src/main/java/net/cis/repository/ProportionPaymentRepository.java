package net.cis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.cis.jpa.entity.ProportionPaymentEntity;

public interface ProportionPaymentRepository extends JpaRepository<ProportionPaymentEntity, String> {
	List<ProportionPaymentEntity> findByDayGreaterThanEqualAndDayLessThanEqual(String fromDate, String toDate);

	@Query(value = "Select ticket from ProportionPaymentEntity ticket where (cppId in :cppId) AND ( day >= :fromDate AND day <= :toDate ) GROUP BY code")
	List<ProportionPaymentEntity> findByProportionPaymentEntity(@Param("cppId") List<Long> cppId,
			@Param("fromDate") String fromDate, @Param("toDate") String toDate);

	@Query(value = "Select ticket from ProportionPaymentEntity ticket where (cppId =:cppId) AND ( day >= :fromDate AND day <= :toDate ) GROUP BY code")
	ProportionPaymentEntity findByProportionPaymentEntity(@Param("cppId") Long cppId,
			@Param("fromDate") String fromDate, @Param("toDate") String toDate);

}

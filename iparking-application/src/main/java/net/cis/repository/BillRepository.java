package net.cis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.BillEntity;

public interface BillRepository extends JpaRepository<BillEntity, Long> {
	List<BillEntity> findBillByCustomerAndStatus(Long customer, Integer status);
}

package net.cis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import net.cis.jpa.entity.CompanyInforEntity;

/**
 * Created by NhanNguyen
 */

public interface CompanyInforRepository extends JpaRepository<CompanyInforEntity, Long> {
	List<CompanyInforEntity> findByMonthlyInvoice(int monthlyInvoice);
}

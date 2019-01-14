package net.cis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.CompanyEntity;

/**
 * Created by Vincent 02/10/2018
 */

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
	CompanyEntity findByCompanyCodeIgnoreCase(String companyCode);
}

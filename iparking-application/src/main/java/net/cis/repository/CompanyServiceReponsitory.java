package net.cis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.CompanyServiceEntity;

public interface CompanyServiceReponsitory extends JpaRepository<CompanyServiceEntity, Integer>{
	List<CompanyServiceEntity> findConfigByCompanyId(int id);
	
	List<CompanyServiceEntity> findByServiceId(int serviceId);
}

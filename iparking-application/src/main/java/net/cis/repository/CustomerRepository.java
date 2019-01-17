package net.cis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
	CustomerEntity findByPhone2(String phone2);

	CustomerEntity findByOldId(long oldId);

	CustomerEntity findById(long id);
}
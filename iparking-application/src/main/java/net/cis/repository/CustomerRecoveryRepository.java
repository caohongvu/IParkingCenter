package net.cis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.CustomerEntity;

public interface CustomerRecoveryRepository extends JpaRepository<CustomerEntity, Long> {

}

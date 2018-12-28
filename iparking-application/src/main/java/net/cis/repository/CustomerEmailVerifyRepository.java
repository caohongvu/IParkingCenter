package net.cis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.CustomerEmailVerifyEntity;

public interface CustomerEmailVerifyRepository extends JpaRepository<CustomerEmailVerifyEntity, Long> {

}

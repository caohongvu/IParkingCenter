package net.cis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.CustomerEmailVerifyViewEntity;

public interface CustomerEmailVerifyRepository extends JpaRepository<CustomerEmailVerifyViewEntity, Long> {

}

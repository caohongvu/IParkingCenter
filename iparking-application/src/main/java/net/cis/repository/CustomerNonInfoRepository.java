package net.cis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.CustomerNonInfoViewEntity;

public interface CustomerNonInfoRepository extends JpaRepository<CustomerNonInfoViewEntity, Long> {

}

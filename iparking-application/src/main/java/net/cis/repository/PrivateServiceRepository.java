package net.cis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.PrivateService;

public interface PrivateServiceRepository extends JpaRepository<PrivateService, Long> {

}

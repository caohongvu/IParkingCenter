package net.cis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.PrivateServicesEntity;

public interface PrivateServiceRepository extends JpaRepository<PrivateServicesEntity, Long> {

}

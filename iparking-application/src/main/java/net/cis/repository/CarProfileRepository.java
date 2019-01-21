package net.cis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.CarProfileEntity;

public interface CarProfileRepository extends JpaRepository<CarProfileEntity, Long> {

	CarProfileEntity findByNumberPlateAndSeatsAndPClass(String numberPlace, Integer seat, String pClass);
}

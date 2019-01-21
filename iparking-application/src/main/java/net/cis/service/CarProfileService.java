package net.cis.service;

import net.cis.dto.CarProfileDto;

public interface CarProfileService {
	CarProfileDto findByNumberPlateAndSeatsAndPClass(String numberPlace, Integer seat, String pClass);

	CarProfileDto save(CarProfileDto carProfileDto);
}

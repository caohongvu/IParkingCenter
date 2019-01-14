package net.cis.service;

import net.cis.dto.ParkingInfoDto;

public interface ParkingInfoService {
	ParkingInfoDto findByCppId(long cppId);
}

package net.cis.service;

import java.util.List;

import net.cis.dto.ParkingConfigDto;
import net.cis.dto.ParkingConfigTypeDto;

public interface ParkingConfigService {

	List<ParkingConfigTypeDto> getParkingConfigTypes(Integer status);

	ParkingConfigTypeDto findParkingConfigTypeById(long id);

	ParkingConfigTypeDto saveParkingConfigType(ParkingConfigTypeDto request);

	List<ParkingConfigDto> getParkingConfig(long parkingConfigType);

	ParkingConfigDto findParkingConfigById(long id);

	ParkingConfigDto saveParkingConfigType(ParkingConfigDto request);

}

package net.cis.service;

import java.util.List;

import net.cis.dto.ParkingConfigDto;
import net.cis.dto.ParkingConfigTypeDto;

public interface ParkingConfigService {

	List<ParkingConfigTypeDto> getParkingConfigTypes(Integer status);

	List<ParkingConfigTypeDto> getParkingConfigTypes(Integer status, String name);

	ParkingConfigTypeDto findParkingConfigTypeById(long id);

	ParkingConfigTypeDto saveParkingConfigType(ParkingConfigTypeDto request);

	List<ParkingConfigDto> getParkingConfig(long parkingConfigType);

	List<ParkingConfigDto> getParkingConfig(String configKey, Long company, Long configType);

	ParkingConfigDto findParkingConfigById(long id);

	ParkingConfigDto saveParkingConfig(ParkingConfigDto request);

}

package net.cis.service;

import java.util.List;

import net.cis.dto.HistoryParkingDto;
import net.cis.dto.ParkingDto;

public interface HistoryParkingService {
	List<HistoryParkingDto> findAll();
	
	List<HistoryParkingDto> findByOldId(String oldId);

	
	HistoryParkingDto save(HistoryParkingDto parkingDto);



}

package net.cis.service;

import java.util.List;

import org.json.JSONException;

import net.cis.dto.ParkingDto;
import net.cis.jpa.entity.ParkingEntity;

/**
 * Created by Vincent 15/11/2018
 */
public interface ParkingService {

	ParkingDto save(ParkingDto parkingDto);

	ParkingDto findById(long id);

	void delete(ParkingDto parkingDto);

	List<ParkingDto> findAll();

	ParkingDto findByOldId(String oldId);

	ParkingDto findByParkingCode(String parkingCode);

	List<ParkingDto> findByCompany(long company);
	
	List<ParkingEntity> findByCompanyId(int companyId);
	
	ParkingDto updateParkingPlace(ParkingDto parkingDto) throws JSONException;
}

package net.cis.service;

import java.util.List;

import net.cis.dto.PrivateServicesParkingCusDto;
import net.cis.dto.PrivateServicesParkingDto;

public interface PrivateServicesService {

	// PrivateServicesParking
	List<PrivateServicesParkingDto> getPrivateServiceParkings(Long parkingId, Integer status);

	PrivateServicesParkingDto findPrivateServicesParking(Long parkingId, Long privateServiceId, Integer status);

	// PrivateServicesParkingCus
	List<PrivateServicesParkingCusDto> getPrivateServicesParkingCus(Long customer);

	PrivateServicesParkingCusDto findPrivateServicesParkingCusById(Long id);

	PrivateServicesParkingCusDto savePrivateServicesParkingCus(
			PrivateServicesParkingCusDto privateServicesParkingCusDto);

	// PrivateServices
}

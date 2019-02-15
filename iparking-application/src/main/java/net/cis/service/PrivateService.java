package net.cis.service;

import java.util.List;

import net.cis.dto.BillDto;
import net.cis.dto.PrivateServicesDto;
import net.cis.dto.PrivateServicesParkingCusDto;
import net.cis.dto.PrivateServicesParkingCusViewDto;
import net.cis.dto.PrivateServicesParkingDto;

public interface PrivateService {

	// PrivateServicesParking
	List<PrivateServicesParkingDto> getPrivateServiceParkings(Long parkingId, Integer status);

	PrivateServicesParkingDto findPrivateServicesParking(Long parkingId, Long privateServiceId, Integer status);

	PrivateServicesParkingDto findPrivateServicesParking(Long parkingId, Long privateServiceId);

	PrivateServicesParkingDto findPrivateServicesParking(Long id);

	PrivateServicesParkingDto savePrivateServicesParking(PrivateServicesParkingDto dto);

	// PrivateServicesParkingCus
	List<PrivateServicesParkingCusViewDto> getPrivateServicesParkingCus(Long customer);

	PrivateServicesParkingCusDto findPrivateServicesParkingCusById(Long id);

	PrivateServicesParkingCusDto savePrivateServicesParkingCus(
			PrivateServicesParkingCusDto privateServicesParkingCusDto);

	// PrivateServices
	List<PrivateServicesDto> getPrivateServices();

	// bill customer
	List<BillDto> getBillCusstomers(Long customerId, Integer status);

}

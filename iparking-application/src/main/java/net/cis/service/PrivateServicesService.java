package net.cis.service;

import java.util.List;

import net.cis.dto.PrivateServicesParkingCustomerDto;
import net.cis.dto.PrivateServicesParkingDto;

public interface PrivateServicesService {
	List<PrivateServicesParkingDto> getPrivateServiceParkings(Long parkingId, Integer status);

	List<PrivateServicesParkingCustomerDto> getPrivateServicesParkingCustomers(Long customer);

	PrivateServicesParkingCustomerDto findPrivateServicesParkingCustomerById(Long id);

	PrivateServicesParkingCustomerDto savePrivateServicesParkingCustomer(
			PrivateServicesParkingCustomerDto objPrivateServicesParkingCustomerDto);

	PrivateServicesParkingDto findPrivateServicesParking(Long parkingId, Long privateServiceId, Integer status);
}

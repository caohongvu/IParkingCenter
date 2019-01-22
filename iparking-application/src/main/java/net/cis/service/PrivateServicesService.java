package net.cis.service;

import java.util.List;

import net.cis.dto.PrivateServicesParkingDto;

public interface PrivateServicesService {
	List<PrivateServicesParkingDto> getPrivateServiceParkings(Long parkingId, Integer status);
}

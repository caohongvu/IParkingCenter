package net.cis.service.impl;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cis.dto.ParkingInfoDto;
import net.cis.jpa.entity.ParkingInfoEntity;
import net.cis.repository.ParkingInfoRepository;
import net.cis.service.ParkingInfoService;

@Service
public class ParkingInfoServiceImpl implements ParkingInfoService {

	@Autowired
	private ParkingInfoRepository parkingInfoRepository;

	ModelMapper mapper;

	@PostConstruct
	public void initialize() {
		mapper = new ModelMapper();
	}

	@Override
	public ParkingInfoDto findByCppId(long cppId) {
		ParkingInfoEntity entity = parkingInfoRepository.findByCarppId(cppId);
		if (entity == null) {
			return null;
		}
		ParkingInfoDto objParkingInfoDto = new ParkingInfoDto();
		mapper.map(entity, objParkingInfoDto);
		return objParkingInfoDto;
	}

}

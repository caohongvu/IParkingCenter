package net.cis.service.impl;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cis.dto.CarProfileDto;
import net.cis.jpa.entity.CarProfileEntity;
import net.cis.repository.CarProfileRepository;
import net.cis.service.CarProfileService;

@Service
public class CarProfileServiceImpl implements CarProfileService {
	@Autowired
	CarProfileRepository carProfileRepository;
	ModelMapper mapper;

	@PostConstruct
	public void initialize() {
		mapper = new ModelMapper();
	}

	@Override
	public CarProfileDto findByNumberPlateAndSeatsAndPClass(String numberPlace, Integer seat, String pClass) {
		CarProfileEntity objCarProfileEntity = carProfileRepository.findByNumberPlateAndSeatsAndPClass(numberPlace,
				seat, pClass);
		if (objCarProfileEntity == null)
			return null;
		CarProfileDto objCarProfileDto = new CarProfileDto();
		mapper.map(objCarProfileEntity, objCarProfileDto);
		return objCarProfileDto;
	}

	@Override
	public CarProfileDto save(CarProfileDto carProfileDto) {
		CarProfileEntity objCarProfileEntity = new CarProfileEntity();
		mapper.map(carProfileDto, objCarProfileEntity);
		mapper.map(carProfileRepository.save(objCarProfileEntity), carProfileDto);
		return carProfileDto;
	}

}

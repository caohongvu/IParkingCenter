package net.cis.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cis.dto.ParkingContractDto;
import net.cis.jpa.entity.ParkingContractEntity;
import net.cis.repository.ParkingContractRepository;
import net.cis.service.ParkingContractService;

/**
 * Created by Vincent on 02/10/2018
 */
@Service
public class ParkingContractServiceImpl implements ParkingContractService {

	

	ModelMapper mapper;

	@Autowired
	ParkingContractRepository parkingContractRepository;
	
	@Override
	public ParkingContractDto findOne(long id) {
		ParkingContractEntity entity = parkingContractRepository.findOne(id);
		ParkingContractDto dto = new ParkingContractDto();
		mapper.map(entity, dto);
		
		return dto;
	}
	
	@Override
	public List<ParkingContractDto> findByCustomer(Long cusId) {
		List<ParkingContractEntity> entities = parkingContractRepository.findByCusId(cusId);
		
		return this.map(entities);
	}

	@Override
	public ParkingContractDto save(ParkingContractDto parkingContractDto) {
		
		ParkingContractEntity entity = new ParkingContractEntity();
		mapper.map(parkingContractDto, entity);
		parkingContractRepository.save(entity);
		
		mapper.map(entity, parkingContractDto);
		return parkingContractDto;
	}
	
	@Override
	public ParkingContractDto update(ParkingContractDto parkingContractDto) {
		
		return save(parkingContractDto);
	}

	
	public List<ParkingContractDto> map(List<ParkingContractEntity> source) {
		
		ArrayList<ParkingContractDto> rtn = new ArrayList<>();
		source.stream().map((entity) -> {
			ParkingContractDto dto = new ParkingContractDto();
			mapper.map(entity, dto);
			return dto;
		}).forEachOrdered((dto) -> {
			rtn.add(dto);
		});
		return rtn;
	}

	

	@PostConstruct
	public void initialize() {
		mapper = new ModelMapper();
	}


	


	



}

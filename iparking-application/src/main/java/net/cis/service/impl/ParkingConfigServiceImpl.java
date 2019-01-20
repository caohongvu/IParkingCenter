package net.cis.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cis.dto.ParkingConfigDto;
import net.cis.dto.ParkingConfigTypeDto;
import net.cis.jpa.entity.ParkingConfigEntity;
import net.cis.jpa.entity.ParkingConfigTypeEntity;
import net.cis.repository.ParkingConfigRepository;
import net.cis.repository.ParkingConfigTypeRepository;
import net.cis.service.ParkingConfigService;

@Service
public class ParkingConfigServiceImpl implements ParkingConfigService {
	ModelMapper mapper;

	@Autowired
	private ParkingConfigTypeRepository parkingConfigTypeRepository;

	@Autowired
	private ParkingConfigRepository parkingConfigRepository;

	@PostConstruct
	public void initialize() {
		mapper = new ModelMapper();
	}

	@Override
	public List<ParkingConfigTypeDto> getParkingConfigTypes(Integer status) {
		List<ParkingConfigTypeEntity> entities = parkingConfigTypeRepository.findByStatus(status);
		return this.map(entities);
	}

	private List<ParkingConfigTypeDto> map(List<ParkingConfigTypeEntity> source) {

		List<ParkingConfigTypeDto> rtn = new ArrayList<>();
		source.stream().map((entity) -> {
			ParkingConfigTypeDto dto = new ParkingConfigTypeDto();
			mapper.map(entity, dto);
			return dto;
		}).forEachOrdered((dto) -> {
			rtn.add(dto);
		});
		return rtn;
	}

	@Override
	public ParkingConfigTypeDto findParkingConfigTypeById(long id) {
		ParkingConfigTypeDto objParkingConfigTypeDto = new ParkingConfigTypeDto();
		ParkingConfigTypeEntity entity = parkingConfigTypeRepository.findOne(id);
		if (entity == null)
			return null;
		mapper.map(entity, objParkingConfigTypeDto);
		return objParkingConfigTypeDto;
	}

	@Override
	public ParkingConfigTypeDto saveParkingConfigType(ParkingConfigTypeDto request) {
		ParkingConfigTypeEntity entity = new ParkingConfigTypeEntity();
		mapper.map(request, entity);
		mapper.map(parkingConfigTypeRepository.save(entity), request);
		return request;
	}

	@Override
	public List<ParkingConfigDto> getParkingConfig(long configKey) {
		List<ParkingConfigEntity> lstParkingConfigEntity = parkingConfigRepository.findByParkingConfigTypeId(configKey);
		return this.mapParkingConfigDto(lstParkingConfigEntity);
	}

	private List<ParkingConfigDto> mapParkingConfigDto(List<ParkingConfigEntity> source) {

		List<ParkingConfigDto> rtn = new ArrayList<>();
		source.stream().map((entity) -> {
			ParkingConfigDto dto = new ParkingConfigDto();
			mapper.map(entity, dto);
			return dto;
		}).forEachOrdered((dto) -> {
			rtn.add(dto);
		});
		return rtn;
	}

	@Override
	public ParkingConfigDto findParkingConfigById(long id) {
		ParkingConfigDto objParkingConfigTypeDto = new ParkingConfigDto();
		ParkingConfigEntity entity = parkingConfigRepository.findOne(id);
		if (entity == null)
			return null;
		mapper.map(entity, objParkingConfigTypeDto);
		return objParkingConfigTypeDto;
	}

	@Override
	public ParkingConfigDto saveParkingConfig(ParkingConfigDto request) {
		ParkingConfigEntity entity = new ParkingConfigEntity();
		mapper.map(request, entity);
		mapper.map(parkingConfigRepository.save(entity), request);
		return request;
	}

	@Override
	public List<ParkingConfigTypeDto> getParkingConfigTypes(Integer status, String name) {
		List<ParkingConfigTypeEntity> entities = parkingConfigTypeRepository
				.findByStatusAndNameIgnoreCaseContaining(status, name);
		return this.map(entities);
	}

	@Override
	public List<ParkingConfigDto> getParkingConfig(String configKey, Long company, Long configType) {
		List<ParkingConfigEntity> entities = parkingConfigRepository.findByParkingConfigs(configKey, company,
				configType);
		return this.mapParkingConfigDto(entities);
	}
}

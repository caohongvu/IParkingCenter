package net.cis.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cis.dto.HistoryParkingDto;
import net.cis.dto.ParkingDto;
import net.cis.jpa.entity.ParkingEntity;
import net.cis.jpa.entity.ParkingPlaceHistoryEntity;
import net.cis.repository.ParkingPlaceHistoryRepository;
import net.cis.service.HistoryParkingService;
import net.cis.service.cache.ParkingPlaceCache;

@Service
public class HistoryParkingServiceImpl implements HistoryParkingService{
	
	ModelMapper mapper;
	
	@Autowired
	private ParkingPlaceHistoryRepository parkingPlaceHistoryRepository;
	
	
	private List<HistoryParkingDto> map(List<ParkingPlaceHistoryEntity> source) {

		ArrayList<HistoryParkingDto> rtn = new ArrayList<>();
		source.stream().map((entity) -> {
			HistoryParkingDto dto = new HistoryParkingDto();
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
		List<HistoryParkingDto> dtos = this.findAll();
		
	}

	@Override
	public List<HistoryParkingDto> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HistoryParkingDto save(HistoryParkingDto historyParkingDto) {
		ModelMapper mapper = new ModelMapper();
		ParkingPlaceHistoryEntity entity = new ParkingPlaceHistoryEntity();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		mapper.map(historyParkingDto, entity);
		
		ParkingPlaceHistoryEntity obj = parkingPlaceHistoryRepository.save(entity);
		
		mapper.map(obj,historyParkingDto);
		return historyParkingDto;
	}

	@Override
	public List<HistoryParkingDto> findByOldId(String oldId) {
		// TODO Auto-generated method stub
		List<ParkingPlaceHistoryEntity> historis = parkingPlaceHistoryRepository.findByOldId(oldId);
		List<HistoryParkingDto> historyDtos = this.map(historis);
		return historyDtos;
	}

}

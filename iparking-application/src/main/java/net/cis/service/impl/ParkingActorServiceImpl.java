package net.cis.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cis.dto.ParkingActorDto;
import net.cis.jpa.entity.ParkingActorEntity;
import net.cis.repository.ParkingActorRepository;
import net.cis.service.ParkingActorService;

/**
 * Created by Vincent on 02/10/2018
 */
@Service
public class ParkingActorServiceImpl implements ParkingActorService {

	@Autowired
	ParkingActorRepository parkingActorRepository;
	
	ModelMapper mapper;
	
	@Override
	public List<ParkingActorDto> findByActors(long actorId) {
		List<ParkingActorEntity> entities = parkingActorRepository.findByActor(actorId);
		
		return this.map(entities);
	}

	
	private List<ParkingActorDto> map(List<ParkingActorEntity> source) {
		
		ArrayList<ParkingActorDto> rtn = new ArrayList<>();
		source.stream().map((entity) -> {
			ParkingActorDto dto = new ParkingActorDto();
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

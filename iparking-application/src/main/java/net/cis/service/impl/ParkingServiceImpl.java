package net.cis.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.json.JSONException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cis.dto.ParkingDto;
import net.cis.dto.ParkingSynDto;
import net.cis.jpa.entity.ParkingEntity;
import net.cis.repository.ParkingRepository;
import net.cis.service.ParkingService;
import net.cis.service.cache.ParkingPlaceCache;

/**
 * Created by Vincent on 02/10/2018
 */
@Service
public class ParkingServiceImpl implements ParkingService {

	@Autowired
	private ParkingRepository parkingRepository;

	@Autowired
	private ParkingPlaceCache parkingPlaceCache;

	ModelMapper mapper;

	@Override
	public ParkingDto save(ParkingDto ticketDto) {
		ModelMapper mapper = new ModelMapper();
		ParkingEntity entity = new ParkingEntity();
		mapper.map(ticketDto, entity);
		mapper.map(parkingRepository.save(entity), ticketDto);
		return ticketDto;
	}

	@Override
	public ParkingDto findById(long id) {
		ModelMapper mapper = new ModelMapper();
		ParkingEntity entity = parkingRepository.findOne(id);
		if (entity == null) {
			return null;
		}
		ParkingDto parkingDto = new ParkingDto();
		mapper.map(entity, parkingDto);
		return parkingDto;
	}

	@Override
	public List<ParkingDto> findAll() {
		List<ParkingEntity> parkingEntities = parkingRepository.findAll();
		List<ParkingDto> parkingDtos = this.map(parkingEntities);
		return parkingDtos;
	}

	private List<ParkingDto> map(List<ParkingEntity> source) {

		ArrayList<ParkingDto> rtn = new ArrayList<>();
		source.stream().map((entity) -> {
			ParkingDto dto = new ParkingDto();
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
		List<ParkingDto> dtos = this.findAll();
		for (ParkingDto dto : dtos) {
			this.parkingPlaceCache.put(dto.getOldId(), dto);
			this.parkingPlaceCache.put(dto.getParkingCode(), dto);
		}
	}

	@Override
	public void delete(ParkingDto parkingDto) {
		if (parkingDto != null && parkingDto.getId() > 0) {
			parkingRepository.delete(parkingDto.getId());
		}
	}

	@Override
	public ParkingDto findByOldId(String oldId) {
		ModelMapper mapper = new ModelMapper();
		ParkingEntity entity = parkingRepository.findByOldId(oldId);
		if (entity == null) {
			return null;
		}
		ParkingDto parkingDto = new ParkingDto();
		mapper.map(entity, parkingDto);
		return parkingDto;
	}

	@Override
	public ParkingDto findByParkingCode(String parkingCode) {
		ModelMapper mapper = new ModelMapper();
		ParkingEntity entity = parkingRepository.findByParkingCodeIgnoreCase(parkingCode);
		if (entity == null) {
			return null;
		}
		ParkingDto parkingDto = new ParkingDto();
		mapper.map(entity, parkingDto);
		return parkingDto;
	}

	@Override
	public List<ParkingDto> findByCompany(long company) {
		List<ParkingEntity> parkingEntities = parkingRepository.findByCompany(company);
		List<ParkingDto> parkingDtos = this.map(parkingEntities);
		return parkingDtos;
	}
	
	@Override
	public List<ParkingEntity> findByCompanyId(int companyId) {
		List<ParkingEntity> parkingEntities = parkingRepository.findByCompany(companyId);
		return parkingEntities;
	}

	@Override
	public ParkingDto updateParkingPlace(ParkingDto parkingDto) throws JSONException {

//		String detailUserURL = "https://admapi.live.iparkingstg.com/r/carpp/moderator/detail?cpp_id="+parkingDto.getOldId();
//	
//		List<NameValuePair> formParams = new ArrayList<>();
//		
//		String responseContent =  RestfulUtil.getWithOutAccessToke(detailUserURL, null);
//		JSONObject jsonObject = new JSONObject(responseContent);
		
		ParkingEntity entity = new ParkingEntity();
		mapper.map(parkingDto, entity);
		ParkingEntity oBjEntity = parkingRepository.save(entity);
		mapper.map(oBjEntity, parkingDto);		
		return parkingDto;
	}
	
	
	//create parking place
	@Override
	public ParkingSynDto create(ParkingSynDto parkingSysDto) {
		
	    Date createdAt = null;
		try {
			createdAt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(parkingSysDto.getCreatedAt());
		} catch (ParseException e) {
			e.printStackTrace();
		}  

		ParkingDto parkingDto = new ParkingDto();
		
		parkingDto.setParkingCode(parkingSysDto.getParkingCode());
		parkingDto.setAddress(parkingSysDto.getAddress());
		parkingDto.setProvince(parkingSysDto.getProvince());
		parkingDto.setDistrict(Long.valueOf(parkingSysDto.getDistrict()));
		
		parkingDto.setStatus(parkingSysDto.getStatus());
		parkingDto.setIparkingJoined(createdAt);
		parkingDto.setCapacity(parkingSysDto.getCapacity());
		
		parkingDto.setAdjust(parkingSysDto.getAdjust());
//		parkingDto.setCurrentTicketInSession(0);
		parkingDto.setParkingPlaceData("");
		parkingDto.setCreatedAt(createdAt);
		parkingDto.setUpdatedAt(createdAt);
		parkingDto.setCompany(0);
		
		parkingDto.setLat(parkingSysDto.getLat());
		parkingDto.setLng(parkingSysDto.getLng());
		parkingDto.setOldId(parkingSysDto.getOldId());
		parkingDto.setPhone(parkingSysDto.getPhone());
		parkingDto.setParkingName("");
		
		ParkingEntity entity = new ParkingEntity();
		
		mapper.map(parkingDto,entity);
		ParkingEntity obj = parkingRepository.save(entity);
		
		mapper.map(obj, parkingDto);
		return null;
	}

}

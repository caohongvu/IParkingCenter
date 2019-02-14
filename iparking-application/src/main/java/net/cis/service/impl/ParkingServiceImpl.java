package net.cis.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cis.dto.ParkingDto;
import net.cis.dto.ParkingSynDto;
import net.cis.jpa.entity.ParkingActorEntity;
import net.cis.jpa.entity.ParkingEntity;
import net.cis.jpa.entity.ParkingInfoEntity;
import net.cis.jpa.entity.ParkingPlaceFeatureEntity;
import net.cis.jpa.entity.ParkingPlaceServiceEntity;
import net.cis.repository.ParkingActorRepository;
import net.cis.repository.ParkingInfoRepository;
import net.cis.repository.ParkingPlaceFeatureReponsitory;
import net.cis.repository.ParkingPlaceServiceRepository;
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

	@Autowired
	private ParkingActorRepository parkingActorReponsitory;

	@Autowired
	private ParkingPlaceServiceRepository parkingPlaceServiceRepository;

	@Autowired
	private ParkingPlaceFeatureReponsitory parkingPlaceFeatureServiceRepository;

	@Autowired
	private ParkingInfoRepository parkingInfoRepository;

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
			this.parkingPlaceCache.put(String.valueOf(dto.getOldId()), dto);
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
	public ParkingDto findByOldId(long oldId) {
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

		// String detailUserURL =
		// "https://admapi.live.iparkingstg.com/r/carpp/moderator/detail?cpp_id="+parkingDto.getOldId();
		//
		// List<NameValuePair> formParams = new ArrayList<>();
		//
		// String responseContent =
		// RestfulUtil.getWithOutAccessToke(detailUserURL,
		// null);
		// JSONObject jsonObject = new JSONObject(responseContent);

		ParkingEntity entity = new ParkingEntity();
		mapper.map(parkingDto, entity);
		ParkingEntity oBjEntity = parkingRepository.save(entity);
		mapper.map(oBjEntity, parkingDto);
		return parkingDto;
	}

	// create parking place
	@Override
	public ParkingSynDto create(ParkingSynDto parkingSysDto) throws JSONException {

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
		// parkingDto.setCurrentTicketInSession(0);
		parkingDto.setParkingPlaceData("");
		parkingDto.setCreatedAt(createdAt);
		parkingDto.setUpdatedAt(createdAt);
		parkingDto.setCompany(0);

		parkingDto.setLat(parkingSysDto.getLat());
		parkingDto.setLng(parkingSysDto.getLng());
		parkingDto.setOldId(Long.parseLong(parkingSysDto.getOldId()));
		parkingDto.setPhone(parkingSysDto.getPhone());
		parkingDto.setParkingName("");

		ParkingEntity entity = new ParkingEntity();

		mapper.map(parkingDto, entity);

		JSONObject jsonObject = new JSONObject(parkingSysDto);

		String listServiceStr = jsonObject.getString("listService");

		JSONArray arrList = new JSONArray(listServiceStr);

		for (int i = 0; i < arrList.length(); i++) {
			ParkingPlaceServiceEntity serviceEntity = new ParkingPlaceServiceEntity();

			JSONObject obj = arrList.getJSONObject(i);
			serviceEntity.setCid(obj.getString("CppId"));
			serviceEntity.setSid(Integer.parseInt(obj.getString("SId")));
			parkingPlaceServiceRepository.save(serviceEntity);
		}

		ParkingEntity obj = parkingRepository.save(entity);

		mapper.map(obj, parkingDto);
		return null;
	}

	@Override
	public ParkingSynDto updateAssignProvider(ParkingSynDto parkingSynDto) throws JSONException {
		ParkingDto parkingDto = new ParkingDto();
		Date updatedAt = null;
		try {
			updatedAt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(parkingSynDto.getUpdatedAt());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		ParkingEntity entity = parkingRepository.findByOldId(Long.parseLong(parkingSynDto.getOldId()));

		mapper.map(entity, parkingDto);

		entity.setUpdatedAt(updatedAt);
		entity.setCompany(parkingSynDto.getCompany());

		ParkingEntity obj = parkingRepository.save(entity);

		mapper.map(obj, parkingDto);
		return parkingSynDto;
	}

	@Override
	public ParkingSynDto updateParkingPlace(ParkingSynDto parkingSynDto) throws JSONException {
		ParkingActorEntity actor = new ParkingActorEntity();
		Date updatedAt = null;

		ParkingEntity entity = parkingRepository.findByOldId(Long.parseLong(parkingSynDto.getOldId()));

		// MAN HINH UPDATE PROVIDERID
		if (parkingSynDto.getUpdateCompany() == 1) {
			try {
				updatedAt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(parkingSynDto.getUpdatedAt());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			ParkingDto parkingDto = new ParkingDto();
			mapper.map(entity, parkingDto);
			entity.setUpdatedAt(updatedAt);
			entity.setCompany(parkingSynDto.getCompany());
			ParkingEntity obj = parkingRepository.save(entity);

		}
		// MAN HINH UPDATE PERMISSION
		if (parkingSynDto.getUpdatePerms() == 1) {
			JSONObject jsonObject = new JSONObject(parkingSynDto);
			String listPermsStr = jsonObject.getString("listPerm");

			JSONArray arrList = new JSONArray(listPermsStr);
			// get list perm from golang
			List<ParkingActorEntity> listAll = new ArrayList<ParkingActorEntity>();

			ArrayList<Long> listActorIdAll = new ArrayList<Long>();
			for (int i = 0; i < arrList.length(); i++) {
				ParkingActorEntity actorEntity = new ParkingActorEntity();
				JSONObject obj = arrList.getJSONObject(i);
				long actorId = Long.valueOf(obj.getString("Actor"));
				actorEntity.setActor(actorId);
				actorEntity.setBalanceAccountId(0);
				actorEntity.setCppId(Long.valueOf(parkingSynDto.getOldId()));
				listAll.add(actorEntity);
			}

			// get list perm java
			List<ParkingActorEntity> listActorCurrent = parkingActorReponsitory
					.findByCppId(Long.valueOf(parkingSynDto.getOldId()));

			// List<ParkingActorEntity> listAdd = new
			// ArrayList<ParkingActorEntity>();
			HashSet<ParkingActorEntity> listAdd = new HashSet<ParkingActorEntity>();
			for (int i = 0; i < listAll.size(); i++) {
				for (int j = 0; j < listActorCurrent.size(); j++) {
					if (listAll.get(i).getActor() == listActorCurrent.get(j).getActor()) {
						listAdd.add(listAll.get(i));
					}
				}
			}

			//
			for (int i = 0; i < listAll.size(); i++) {
				listAll.add(listAll.get(i));
			}

			// delete all perm of java
			for (int i = 0; i < listActorCurrent.size(); i++) {
				parkingActorReponsitory.delete(listActorCurrent.get(i));
			}
			// insert all perm of java
			for (int i = 0; i < listActorCurrent.size(); i++) {
				parkingActorReponsitory.save(listActorCurrent.get(i));
			}

		}
		// MAN HINH UPDATE FEATURE
		if (parkingSynDto.getUpdateFeature() == 1) {
			JSONObject jsonObject = new JSONObject(parkingSynDto);

			// ParkingPlaceFeatureEntity entityFeature = null
			String list = jsonObject.getString("listFeature");

			JSONObject objectFeature = new JSONObject(list);
			List<ParkingPlaceFeatureEntity> listAdd = new ArrayList<>();
			for (int i = 1; i < 11; i++) {
				System.out.println(i);
				boolean value = Boolean.parseBoolean(objectFeature.getString(String.valueOf(i)));
				if (value == true) {
					ParkingPlaceFeatureEntity entityFeature = new ParkingPlaceFeatureEntity();
					entityFeature.setFeatureId(i);
					entityFeature.setOldId(parkingSynDto.getOldId());
					listAdd.add(entityFeature);
				}

			}
			// get feature current
			List<ParkingPlaceFeatureEntity> listCurrent = parkingPlaceFeatureServiceRepository
					.findByOldId(parkingSynDto.getOldId());
			for (int i = 0; i < listCurrent.size(); i++) {
				parkingPlaceFeatureServiceRepository.delete(listCurrent.get(i));

			}
			// add feature from golang
			for (int i = 0; i < listAdd.size(); i++) {
				parkingPlaceFeatureServiceRepository.save(listAdd.get(i));
			}
		}
		// MAN HINH UPDATE PAYMENT
		if (parkingSynDto.getUpdatePayment() == 1) {
			JSONObject jsonObject = new JSONObject(parkingSynDto);
			String list = jsonObject.getString("listPayment");
			JSONObject objectPayment = new JSONObject(list);

			String paymentRule = objectPayment.getString("Payment");
			String TTMThreshold = objectPayment.getString("TTMThreshold");

			ParkingInfoEntity infoEntity = new ParkingInfoEntity();
			infoEntity.setPaymentRule(paymentRule);
			infoEntity.setTimeAvg(Integer.parseInt(TTMThreshold));
			infoEntity.setCarppId(Long.valueOf(parkingSynDto.getOldId()));

			parkingInfoRepository.save(infoEntity);

		}
		// MAN HINH UPDATE METADATA
		if (parkingSynDto.getUpdateMetadata() == 1) {
			ParkingInfoEntity infoEntity = new ParkingInfoEntity();
			JSONObject jsonObject = new JSONObject(parkingSynDto);
			System.out.println(jsonObject);
			parkingInfoRepository.save(infoEntity);

		}

		return null;
	}

	@Override
	public List<ParkingDto> findByCarppIds(List<Long> carppIds) {
		List<ParkingEntity> lst = parkingRepository.findByOldIdIn(carppIds);
		return this.map(lst);
	}

}

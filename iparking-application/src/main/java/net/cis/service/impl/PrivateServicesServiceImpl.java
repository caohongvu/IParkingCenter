package net.cis.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cis.constants.CustomerConstans;
import net.cis.dto.PrivateServicesParkingCusDto;
import net.cis.dto.PrivateServicesParkingDto;
import net.cis.jpa.entity.ParkingEntity;
import net.cis.jpa.entity.PrivateServicesEntity;
import net.cis.jpa.entity.PrivateServicesParkingCusEntity;
import net.cis.jpa.entity.PrivateServicesParkingEntity;
import net.cis.repository.ParkingRepository;
import net.cis.repository.PrivateServiceRepository;
import net.cis.repository.PrivateServicesParkingCusRepository;
import net.cis.repository.PrivateServicesParkingRepository;
import net.cis.service.PrivateServicesService;

@Service
public class PrivateServicesServiceImpl implements PrivateServicesService {
	@Autowired
	PrivateServiceRepository privateServiceRepository;
	@Autowired
	PrivateServicesParkingCusRepository privateServicesParkingCustomerRepository;
	@Autowired
	PrivateServicesParkingRepository privateServicesParkingRepository;

	@Autowired
	ParkingRepository parkingRepository;

	ModelMapper mapper;

	@PostConstruct
	public void initialize() {
		mapper = new ModelMapper();
	}

	/**
	 * Danh sach dich vu theo diem do va trang thang
	 */
	@Override
	public List<PrivateServicesParkingDto> getPrivateServiceParkings(Long parkingId, Integer status) {
		List<PrivateServicesParkingEntity> lst = null;
		if (status == null) {
			lst = privateServicesParkingRepository.findByParkingId(parkingId);
		} else {
			lst = privateServicesParkingRepository.findByParkingIdAndStatus(parkingId, status);
		}
		return this.map(lst);
	}

	private List<PrivateServicesParkingDto> map(List<PrivateServicesParkingEntity> source) {
		List<PrivateServicesParkingDto> rtn = new ArrayList<>();
		source.stream().map((entity) -> {
			PrivateServicesParkingDto dto = new PrivateServicesParkingDto();
			mapper.map(entity, dto);
			return dto;
		}).forEachOrdered((dto) -> {
			rtn.add(dto);
		});
		return rtn;
	}

	/**
	 * Danh sach dich vu theo customer
	 */
	@Override
	public List<PrivateServicesParkingCusDto> getPrivateServicesParkingCus(Long cusId) {
		List<PrivateServicesParkingCusEntity> lst = privateServicesParkingCustomerRepository.findByCusIdAndStatus(cusId,
				CustomerConstans.CUSTOMER_SERVICE_ENABLE);
		return this.map2(lst);
	}

	private List<PrivateServicesParkingCusDto> map2(List<PrivateServicesParkingCusEntity> source) {
		List<PrivateServicesParkingCusDto> rtn = new ArrayList<>();
		source.stream().map((entity) -> {
			PrivateServicesParkingCusDto dto = new PrivateServicesParkingCusDto();
			mapper.map(entity, dto);
			dto.setPrivateServicesParkingDto(getPrivateServicesParkingDetail(dto.getId()));
			return dto;
		}).forEachOrdered((dto) -> {
			rtn.add(dto);
		});
		return rtn;
	}

	/**
	 * Chi tiet dich vu diem do
	 * 
	 * @param parkingServiceParkingId
	 * @return
	 */
	private PrivateServicesParkingDto getPrivateServicesParkingDetail(Long parkingServiceParkingId) {
		PrivateServicesParkingEntity objPrivateServicesParkingEntity = privateServicesParkingRepository
				.findOne(parkingServiceParkingId);

		PrivateServicesParkingDto objPrivateServicesParkingDto = new PrivateServicesParkingDto();
		if (objPrivateServicesParkingEntity != null) {
			// lay thong tin diem dich vu
			ParkingEntity objParkingEntity = parkingRepository
					.findByOldId(String.valueOf(objPrivateServicesParkingEntity.getParkingId()));

			PrivateServicesEntity objPrivateServicesEntity = privateServiceRepository
					.findOne(objPrivateServicesParkingEntity.getPrivateServiceId());

			objPrivateServicesParkingDto.setId(objPrivateServicesParkingEntity.getId());
			objPrivateServicesParkingDto.setParkingId(objPrivateServicesParkingEntity.getParkingId());
			objPrivateServicesParkingDto.setParkingCode(objParkingEntity.getParkingCode());
			objPrivateServicesParkingDto.setPrivateServiceId(objPrivateServicesParkingEntity.getPrivateServiceId());
			objPrivateServicesParkingDto.setParkingServiceName(objPrivateServicesEntity.getName());
			objPrivateServicesParkingDto.setCreatedAt(objPrivateServicesParkingEntity.getCreatedAt());
			objPrivateServicesParkingDto.setStatus(objPrivateServicesParkingEntity.getStatus());

		}
		return objPrivateServicesParkingDto;
	}

	/**
	 * Tim kiem dich vu customer
	 */
	@Override
	public PrivateServicesParkingCusDto findPrivateServicesParkingCusById(Long id) {
		PrivateServicesParkingCusEntity entity = privateServicesParkingCustomerRepository.findOne(id);
		PrivateServicesParkingCusDto dto = new PrivateServicesParkingCusDto();
		mapper.map(entity, dto);
		return dto;
	}

	/**
	 * Them moi cap nhat dich vu customer
	 */
	@Override
	public PrivateServicesParkingCusDto savePrivateServicesParkingCus(
			PrivateServicesParkingCusDto objPrivateServicesParkingCusDto) {
		PrivateServicesParkingCusEntity entity = new PrivateServicesParkingCusEntity();
		mapper.map(objPrivateServicesParkingCusDto, entity);
		mapper.map(privateServicesParkingCustomerRepository.save(entity), objPrivateServicesParkingCusDto);
		return null;
	}

	/**
	 * Tim kiems dich vu cua diem do
	 */
	@Override
	public PrivateServicesParkingDto findPrivateServicesParking(Long parkingId, Long privateServiceId, Integer status) {
		PrivateServicesParkingDto objPrivateServicesParkingDto = new PrivateServicesParkingDto();
		PrivateServicesParkingEntity entity = privateServicesParkingRepository
				.findByParkingIdAndPrivateServiceIdAndStatus(parkingId, privateServiceId, status);
		if (entity == null)
			return null;

		mapper.map(entity, objPrivateServicesParkingDto);
		return objPrivateServicesParkingDto;
	}

}

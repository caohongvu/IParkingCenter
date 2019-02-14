package net.cis.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cis.dto.PrivateServicesDto;
import net.cis.dto.PrivateServicesParkingCusDto;
import net.cis.dto.PrivateServicesParkingCusViewDto;
import net.cis.dto.PrivateServicesParkingDto;
import net.cis.jpa.entity.PrivateServicesEntity;
import net.cis.jpa.entity.PrivateServicesParkingCusEntity;
import net.cis.jpa.entity.PrivateServicesParkingCusViewEntity;
import net.cis.jpa.entity.PrivateServicesParkingEntity;
import net.cis.repository.ParkingRepository;
import net.cis.repository.PrivateServiceRepository;
import net.cis.repository.PrivateServicesParkingCusRepository;
import net.cis.repository.PrivateServicesParkingCusViewRepository;
import net.cis.repository.PrivateServicesParkingRepository;
import net.cis.service.PrivateService;

@Service
public class PrivateServicesServiceImpl implements PrivateService {
	@Autowired
	PrivateServiceRepository privateServiceRepository;
	@Autowired
	PrivateServicesParkingRepository privateServicesParkingRepository;
	@Autowired
	PrivateServicesParkingCusRepository privateServicesParkingCustomerRepository;
	@Autowired
	PrivateServicesParkingCusViewRepository privateServicesParkingCusViewRepository;
	@Autowired
	ParkingRepository parkingRepository;

	ModelMapper mapper;

	@PostConstruct
	public void initialize() {
		mapper = new ModelMapper();
	}

	/**
	 * Danh sach dich vụ
	 */
	@Override
	public List<PrivateServicesDto> getPrivateServices() {
		List<PrivateServicesEntity> lst = privateServiceRepository.findAll();
		return this.mapPrivateServices(lst);
	}

	private List<PrivateServicesDto> mapPrivateServices(List<PrivateServicesEntity> source) {
		List<PrivateServicesDto> rtn = new ArrayList<>();
		source.stream().map((entity) -> {
			PrivateServicesDto dto = new PrivateServicesDto();
			mapper.map(entity, dto);
			return dto;
		}).forEachOrdered((dto) -> {
			rtn.add(dto);
		});
		return rtn;
	}

	/**
	 * Danh sach dich vu theo diem do va trang thai
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
	public List<PrivateServicesParkingCusViewDto> getPrivateServicesParkingCus(Long cusId) {
		List<PrivateServicesParkingCusViewEntity> lst = privateServicesParkingCusViewRepository.findByCusId(cusId);
		return this.map2(lst);
	}

	private List<PrivateServicesParkingCusViewDto> map2(List<PrivateServicesParkingCusViewEntity> source) {
		List<PrivateServicesParkingCusViewDto> rtn = new ArrayList<>();
		source.stream().map((entity) -> {
			PrivateServicesParkingCusViewDto dto = new PrivateServicesParkingCusViewDto();
			mapper.map(entity, dto);
			return dto;
		}).forEachOrdered((dto) -> {
			rtn.add(dto);
		});
		return rtn;
	}

	/**
	 * Tim kiem dich vu customer
	 */
	@Override
	public PrivateServicesParkingCusDto findPrivateServicesParkingCusById(Long id) {
		PrivateServicesParkingCusEntity entity = privateServicesParkingCustomerRepository.findOne(id);

		if (entity == null)
			return null;
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
		return objPrivateServicesParkingCusDto;
	}

	/**
	 * Tim kiems dich vu cua diem do
	 */
	@Override
	public PrivateServicesParkingDto findPrivateServicesParking(Long parkingId, Long privateServiceId, Integer status) {
		PrivateServicesParkingDto objPrivateServicesParkingDto = new PrivateServicesParkingDto();

		PrivateServicesEntity psEntity = new PrivateServicesEntity();
		psEntity.setId(privateServiceId);

		PrivateServicesParkingEntity entity = privateServicesParkingRepository
				.findByParkingIdAndPrivateServicesAndStatus(parkingId, psEntity, status);
		if (entity == null)
			return null;

		mapper.map(entity, objPrivateServicesParkingDto);
		return objPrivateServicesParkingDto;
	}

	/**
	 * Tim kiems dich vu cua diem do
	 */
	@Override
	public PrivateServicesParkingDto findPrivateServicesParking(Long parkingId, Long privateServiceId) {
		PrivateServicesParkingDto objPrivateServicesParkingDto = new PrivateServicesParkingDto();

		PrivateServicesEntity psEntity = new PrivateServicesEntity();
		psEntity.setId(privateServiceId);

		PrivateServicesParkingEntity entity = privateServicesParkingRepository
				.findByParkingIdAndPrivateServices(parkingId, psEntity);
		if (entity == null)
			return null;

		mapper.map(entity, objPrivateServicesParkingDto);
		return objPrivateServicesParkingDto;
	}

	@Override
	public PrivateServicesParkingDto findPrivateServicesParking(Long id) {
		PrivateServicesParkingDto objPrivateServicesParkingDto = new PrivateServicesParkingDto();
		PrivateServicesParkingEntity entity = privateServicesParkingRepository.findOne(id);
		if (entity == null) {
			return null;
		}

		mapper.map(entity, objPrivateServicesParkingDto);
		return objPrivateServicesParkingDto;
	}

	/**
	 * thuc hien them moi dich vu cho diem do
	 */
	@Override
	public PrivateServicesParkingDto savePrivateServicesParking(PrivateServicesParkingDto dto) {
		PrivateServicesParkingEntity entity = new PrivateServicesParkingEntity();
		mapper.map(dto, entity);
		entity = privateServicesParkingRepository.save(entity);
		mapper.map(entity, dto);
		return dto;
	}

}

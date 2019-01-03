package net.cis.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cis.dto.CompanyDto;
import net.cis.jpa.entity.CompanyEntity;
import net.cis.repository.CompanyRepository;
import net.cis.service.CompanyService;
import net.cis.service.cache.CompanyCache;

/**
 * Created by Vincent on 02/10/2018
 */
@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private CompanyCache companyCache;

	ModelMapper mapper;
	
	@Override
	public CompanyDto save(CompanyDto companyDto) {
		ModelMapper mapper = new ModelMapper();
		CompanyEntity entity = new CompanyEntity();
		mapper.map(companyDto, entity);
		mapper.map(companyRepository.save(entity), companyDto);
		return companyDto;
	}
	
	@Override
	public CompanyDto findById(long id) {
		ModelMapper mapper = new ModelMapper();
		CompanyEntity entity = companyRepository.findOne(id);
		if(entity == null) {
			return null;
		}
		CompanyDto companyDto = new CompanyDto();
		mapper.map(entity, companyDto);
		return companyDto;
	}


	@Override
	public List<CompanyDto> findAll() {
		List<CompanyEntity> companyEntities = companyRepository.findAll();
		List<CompanyDto> parkingDtos = this.map(companyEntities);
		return parkingDtos;
	}

	
	private List<CompanyDto> map(List<CompanyEntity> source) {
		
		ArrayList<CompanyDto> rtn = new ArrayList<>();
		source.stream().map((entity) -> {
			CompanyDto dto = new CompanyDto();
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
		List<CompanyDto> dtos = this.findAll();
		for(CompanyDto dto : dtos) {
			this.companyCache.put(dto.getCompanyCode(), dto);
			this.companyCache.put(String.valueOf(dto.getId()), dto);
		}
	}


	@Override
	public void delete(CompanyDto parkingDto) {
		if(parkingDto != null && parkingDto.getId() > 0) {
			companyRepository.delete(parkingDto.getId());
		}
	}

}

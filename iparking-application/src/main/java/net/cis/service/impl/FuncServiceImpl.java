package net.cis.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cis.constants.UserConstans;
import net.cis.dto.FuncDto;
import net.cis.jpa.entity.FuncEntity;
import net.cis.repository.FuncRepository;
import net.cis.service.FuncService;

@Service
public class FuncServiceImpl implements FuncService {
	protected final Logger LOGGER = Logger.getLogger(getClass());

	@Autowired
	private FuncRepository funcRepository;

	ModelMapper mapper;

	@PostConstruct
	public void initialize() {
		mapper = new ModelMapper();
	}

	@Override
	public FuncDto create(FuncDto dto) throws Exception {
		FuncEntity newEntity = new FuncEntity();
		mapper.map(dto, newEntity);
		mapper.map(funcRepository.save(newEntity), dto);
		return dto;
	}

	@Override
	public List<FuncDto> findAll() throws Exception {
		List<FuncEntity> funcEntities = funcRepository.findAll();
		return this.map(funcEntities);
	}

	@Override
	public List<FuncDto> findByStatus(Integer status) throws Exception {
		return this.map(funcRepository.findByStatus(status));
	}

	@Override
	public FuncDto findById(Long id) throws Exception {
		FuncEntity ett = funcRepository.findOne(id);

		if (ett == null) {
			return null;
		}
		return mapper.map(ett, FuncDto.class);
	}

	@Override
	public FuncDto update(FuncDto dto) throws Exception {
		FuncEntity newEntity = new FuncEntity();
		mapper.map(dto, newEntity);
		mapper.map(funcRepository.save(newEntity), dto);
		return dto;
	}

	@Override
	public FuncDto findOneByName(String name) throws Exception {
		FuncEntity ett = funcRepository.findOneByNameIgnoreCase(name);
		if (ett == null) {
			return null;
		}
		return mapper.map(ett, FuncDto.class);
	}

	@Override
	public FuncDto findOneByNameAndIdNot(String name, Long id) throws Exception {
		FuncEntity ett = funcRepository.findOneByNameAndIdNot(name, id);

		if (ett == null) {
			return null;
		}
		return mapper.map(ett, FuncDto.class);
	}

	private List<FuncDto> map(List<FuncEntity> source) {
		List<FuncDto> rtn = new ArrayList<>();
		for (FuncEntity entity : source) {
			FuncDto dto = new FuncDto();
			mapper.map(entity, dto);
			if (entity.getParentId() != null) {
				FuncEntity entityParent = funcRepository.findOne(Long.parseLong(entity.getParentId().toString()));
				if (entityParent != null)
					dto.setParentName(entityParent.getName());
			}
			rtn.add(dto);
		}
		return rtn;
	}

	@Override
	public List<FuncDto> findAllFuncParent() throws Exception {
		List<FuncEntity> funcEntities = funcRepository.findByStatusAndParentIdIsNull(UserConstans.FUNC_STATUS_ENABLE);
		return this.map(funcEntities);
	}
}

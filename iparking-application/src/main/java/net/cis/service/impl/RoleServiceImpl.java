package net.cis.service.impl;

import net.cis.dto.RoleDto;
import net.cis.jpa.entity.RoleEntity;
import net.cis.repository.RoleRepository;
import net.cis.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    ModelMapper mapper;

    @PostConstruct
    public void initialize() {
        mapper = new ModelMapper();
    }

    @Autowired
    RoleRepository roleRepository;

    @Override
    public List<RoleDto> findAll() {
        return this.map(roleRepository.findAll());
    }

    @Override
    public List<RoleDto> findByStatus(Integer status) {
        return this.map(roleRepository.findByStatus(status));
    }

    @Override
    public RoleDto findOne(Long id) {
        return mapper.map(roleRepository.findOne(id), RoleDto.class);
    }

    @Override
    public RoleDto create(RoleDto dto) {
        RoleEntity ett = mapper.map(dto, RoleEntity.class);
        ett.setId(null);
        RoleEntity newEtt = roleRepository.save(ett);
        return mapper.map(newEtt, RoleDto.class);
    }

    @Override
    public void update(RoleDto dto) {
        RoleEntity ett = mapper.map(dto, RoleEntity.class);
        if (ett.getId() != null) {
            roleRepository.save(ett);
        }
    }

    private List<RoleDto> map(List<RoleEntity> entities) {
        List<RoleDto> dtos = new ArrayList<>();
        for (RoleEntity ett: entities) {
            dtos.add(mapper.map(ett, RoleDto.class));
        }
        return dtos;
    }
}

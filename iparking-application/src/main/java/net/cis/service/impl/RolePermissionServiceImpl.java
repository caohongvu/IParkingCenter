package net.cis.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cis.dto.FuncDto;
import net.cis.dto.RoleDto;
import net.cis.dto.RolePermissionDto;
import net.cis.jpa.entity.FuncEntity;
import net.cis.jpa.entity.RoleEntity;
import net.cis.jpa.entity.RolePermissionEntity;
import net.cis.repository.FuncRepository;
import net.cis.repository.RolePermissionRepository;
import net.cis.repository.RoleRepository;
import net.cis.service.RolePermissionService;

@Service
public class RolePermissionServiceImpl implements RolePermissionService {
    @Autowired
    RolePermissionRepository rolePermissionRepository;

    @Autowired
    FuncRepository funcRepository;

    @Autowired
    RoleRepository roleRepository;

    private ModelMapper modelMapper ;

    @PostConstruct
    public void initialize() {
        modelMapper = new ModelMapper();
    }

    @Override
    public List<RolePermissionDto> findAll() {
        return this.map(rolePermissionRepository.findAll());
    }

    @Override
    public List<RolePermissionDto> findByRole(RoleDto role) {
        RoleEntity ett = new RoleEntity();
        ett.setId(role.getId());
        return this.map(rolePermissionRepository.findByRole(ett));
    }

    @Override
    public RolePermissionDto findOneByRoleAndFunc(RoleDto roleDto, FuncDto funcDto) {
        RolePermissionEntity ett = rolePermissionRepository.findOneByRoleAndFunc(modelMapper.map(roleDto, RoleEntity.class), modelMapper.map(funcDto, FuncEntity.class));
        if (ett == null)
            return null;

        return modelMapper.map(ett, RolePermissionDto.class);
    }

    @Override
    public RolePermissionDto findOne(Long id) {
        return modelMapper.map(rolePermissionRepository.findOne(id), RolePermissionDto.class);
    }

    @Override
    public RolePermissionDto create(RolePermissionDto dto) {
        RolePermissionEntity entity = modelMapper.map(dto, RolePermissionEntity.class);
        if (entity.getId() != null) {
            entity.setId(null);
        }

        return modelMapper.map(rolePermissionRepository.save(entity), RolePermissionDto.class);
    }

    @Override
    public RolePermissionDto update(RolePermissionDto dto) {
        RolePermissionEntity entity = modelMapper.map(dto, RolePermissionEntity.class);
        return modelMapper.map(rolePermissionRepository.save(entity), RolePermissionDto.class);
    }

    @Override
    public void delete(RolePermissionDto entity) {
        rolePermissionRepository.delete(entity.getId());
    }

    private List<RolePermissionDto> map(List<RolePermissionEntity> entities) {
        List<RolePermissionDto> dtos = new ArrayList<>();
        for(RolePermissionEntity ett: entities) {
            dtos.add(modelMapper.map(ett, RolePermissionDto.class));
        }
        return dtos;
    }
}

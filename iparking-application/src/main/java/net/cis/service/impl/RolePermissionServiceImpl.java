package net.cis.service.impl;

import net.cis.dto.RolePermissionDto;
import net.cis.jpa.entity.RolePermissionEntity;
import net.cis.repository.FuncRepository;
import net.cis.repository.RolePermissionRepository;
import net.cis.repository.RoleRepository;
import net.cis.service.RolePermissionService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class RolePermissionServiceImpl implements RolePermissionService {
    @Autowired
    RolePermissionRepository rolePermissionRepository;

    @Autowired
    FuncRepository funcRepository;

    @Autowired
    RoleRepository roleRepository;

    private ModelMapper modelMapper;

    @PostConstruct
    public void initialize() {
        modelMapper = new ModelMapper();

        modelMapper.addMappings(new PropertyMap<RolePermissionEntity, RolePermissionDto>() {
            protected void configure() {
                map().setFuncId(source.getFunc().getId());
                map().setFuncName(source.getFunc().getName());
                map().setFuncDesc(source.getFunc().getDesc());

                map().setRole(source.getRole().getId());
                map().setRoleDesc(source.getRole().getDesc());
            }
        });

        modelMapper.addMappings(new PropertyMap<RolePermissionDto, RolePermissionEntity>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setFunc(funcRepository.findOne(source.getFuncId()));
                map().setRole(roleRepository.findOne(source.getRole()));
            }
        });
    }

    @Override
    public List<RolePermissionDto> findAll() {
        return this.map(rolePermissionRepository.findAll());
    }

    @Override
    public List<RolePermissionDto> findByRole(Long role) {
        return this.map(rolePermissionRepository.findByRole(role));
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

    private List<RolePermissionDto> map(List<RolePermissionEntity> entities) {
        List<RolePermissionDto> dtos = new ArrayList<>();
        for(RolePermissionEntity ett: entities) {
            dtos.add(modelMapper.map(ett, RolePermissionDto.class));
        }
        return dtos;
    }
}

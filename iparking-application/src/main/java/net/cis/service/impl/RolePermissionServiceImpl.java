package net.cis.service.impl;

import net.cis.dto.RolePermissionDto;
import net.cis.jpa.entity.RolePermissionEntity;
import net.cis.repository.RolePermissionRepository;
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

    ModelMapper modelMapper;

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

    private List<RolePermissionDto> map(List<RolePermissionEntity> entities) {
        List<RolePermissionDto> dtos = new ArrayList<>();
        for(RolePermissionEntity ett: entities) {
            dtos.add(modelMapper.map(ett, RolePermissionDto.class));
        }
        return dtos;
    }
}

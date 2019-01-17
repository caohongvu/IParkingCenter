package net.cis.service;

import net.cis.dto.RolePermissionDto;
import net.cis.jpa.entity.RolePermissionEntity;

import java.util.List;

public interface RolePermissionService {
    List<RolePermissionDto> findAll();
    List<RolePermissionDto> findByRole(Long role);
    RolePermissionDto findOne(Long id);
//    RolePermissionDto create(RolePermissionDto entity);
//    RolePermissionDto update(RolePermissionDto entity);
}

package net.cis.service;

import net.cis.dto.RolePermissionDto;

import java.util.List;

public interface RolePermissionService {
    List<RolePermissionDto> findAll();
    List<RolePermissionDto> findByRole(Long role);
    RolePermissionDto findOne(Long id);
}

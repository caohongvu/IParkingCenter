package net.cis.service;

import net.cis.dto.FuncDto;
import net.cis.dto.RoleDto;
import net.cis.dto.RolePermissionDto;

import java.util.List;

public interface RolePermissionService {
    List<RolePermissionDto> findAll();
    List<RolePermissionDto> findByRole(RoleDto role);
    RolePermissionDto findOneByRoleAndFunc(RoleDto roleDto, FuncDto funcDto);
    RolePermissionDto findOne(Long id);
    RolePermissionDto create(RolePermissionDto entity);
    RolePermissionDto update(RolePermissionDto entity);
    void delete(RolePermissionDto entity);
}

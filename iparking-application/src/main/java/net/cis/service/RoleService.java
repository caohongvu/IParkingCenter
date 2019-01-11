package net.cis.service;

import net.cis.dto.RoleDto;

import java.util.List;

public interface RoleService {
    List<RoleDto> findAll();
    List<RoleDto> findByStatus(Integer status);
    RoleDto findOne(Long id);
    RoleDto create(RoleDto dto);
    void update(RoleDto dto);
}

package net.cis.service;

import net.cis.dto.FuncDto;

import java.util.List;

public interface FuncService {
    List<FuncDto> findAll() throws Exception;
    List<FuncDto> findByStatus(Integer status) throws Exception;
    FuncDto findById(Long id) throws Exception;
    FuncDto findOneByName(String name) throws Exception;
    FuncDto findOneByNameAndIdNot(String name, Long id) throws Exception;
    FuncDto create(FuncDto dto) throws Exception;
    void update(FuncDto dto) throws Exception;
}
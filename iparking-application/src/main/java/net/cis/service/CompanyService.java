package net.cis.service;

import java.util.List;

import net.cis.dto.CompanyDto;

/**
 * Created by Vincent 15/11/2018
 */
public interface CompanyService {

    CompanyDto save(CompanyDto companyDto);
    
    CompanyDto findById(long id);

    void delete(CompanyDto parkingDto);
    
    List<CompanyDto> findAll();
    
}
 
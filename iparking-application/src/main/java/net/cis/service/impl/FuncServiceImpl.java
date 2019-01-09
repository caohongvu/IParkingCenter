package net.cis.service.impl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import net.cis.dto.FuncDto;
import net.cis.jpa.entity.FuncEntity;
import net.cis.repository.FuncRepository;
import net.cis.service.FuncService;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class FuncServiceImpl implements FuncService {
    protected final Logger LOGGER = Logger.getLogger(getClass());

    @Autowired
    private FuncRepository funcRepository;

    ModelMapper mapper;

    @PostConstruct
    public void initialize() {
        mapper = new ModelMapper();
    }

    @Override
    public List<FuncDto> findAll() {
        List<FuncEntity> funcEntities = funcRepository.findAll();
        return this.map(funcEntities);
    }

    private List<FuncDto> map(List<FuncEntity> source) {
        List<FuncDto> rtn = new ArrayList<>();
        for (FuncEntity entity : source) {
            FuncDto dto = new FuncDto();
            mapper.map(entity, dto);
            rtn.add(dto);
        }
        return rtn;
    }
}

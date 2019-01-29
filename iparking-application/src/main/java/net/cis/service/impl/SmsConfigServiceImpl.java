package net.cis.service.impl;

import net.cis.dto.SmsConfigDto;
import net.cis.jpa.entity.SmsConfigEntity;
import net.cis.repository.SmsConfigRepository;
import net.cis.service.SmsConfigService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class SmsConfigServiceImpl implements SmsConfigService {
    @Autowired
    SmsConfigRepository smsConfigRepository;

    private ModelMapper modelMapper;

    @PostConstruct
    public void initialize() {
        modelMapper = new ModelMapper();
    }

    @Override
    public SmsConfigDto findFirstByOrderByIdAsc() {
        SmsConfigEntity ett = smsConfigRepository.findFirstByOrderByIdAsc();
        if (ett != null) {
            return modelMapper.map(ett, SmsConfigDto.class);
        }

        return null;
    }

    @Override
    public void update(SmsConfigDto dto) {
        if (dto.getId() != null) {
            smsConfigRepository.save(modelMapper.map(dto, SmsConfigEntity.class));
        }
    }
}

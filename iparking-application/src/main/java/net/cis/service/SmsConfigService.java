package net.cis.service;

import net.cis.dto.SmsConfigDto;

public interface SmsConfigService {
    void update(SmsConfigDto dto);
    SmsConfigDto findFirstByOrderByIdAsc();
}

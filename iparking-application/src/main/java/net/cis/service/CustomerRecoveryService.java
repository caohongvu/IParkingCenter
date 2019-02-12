package net.cis.service;

import net.cis.dto.CustomerRecoveryDto;

public interface CustomerRecoveryService {
	CustomerRecoveryDto save(CustomerRecoveryDto customerRecoveryDto);

	CustomerRecoveryDto find(Long cusId, Long checkSum);
}
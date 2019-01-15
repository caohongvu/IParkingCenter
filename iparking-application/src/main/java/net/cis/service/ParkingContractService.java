package net.cis.service;

import java.util.List;

import net.cis.dto.ParkingContractDto;
import net.cis.dto.ParkingContractInfoDto;
import net.cis.dto.ParkingContractOutOfDateDto;
import net.cis.jpa.criteria.ParkingContractCriteria;

/**
 * Created by Vincent 15/11/2018
 */
public interface ParkingContractService {

	ParkingContractDto findOne(long id);

	List<ParkingContractDto> findByCustomer(Long cusId);

	ParkingContractDto save(ParkingContractDto parkingContractDto);

	ParkingContractDto update(ParkingContractDto parkingContractDto);

	List<ParkingContractDto> findAll(ParkingContractCriteria ticketCriteria);

	List<ParkingContractOutOfDateDto> findParkingContractOutOfDate(ParkingContractCriteria ticketCriteria);

	List<ParkingContractInfoDto> findParkingContractInfo(ParkingContractCriteria ticketCriteria);

}

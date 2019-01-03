package net.cis.service;

import net.cis.dto.ParkingContractDto;

/**
 * Created by Vincent 15/11/2018
 */
public interface ParkingContractService {

   
    ParkingContractDto findOne(long id);
    
    ParkingContractDto save(ParkingContractDto parkingContractDto);
    
    ParkingContractDto update(ParkingContractDto parkingContractDto);
    

}
 
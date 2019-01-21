package net.cis.service;

import java.util.List;

import net.cis.dto.ParkingActorDto;
import net.cis.jpa.entity.ParkingActorEntity;

/**
 * Created by Vincent 23/05/2018
 */
public interface ParkingActorService {

    List<ParkingActorDto> findByActors(long actorId);
    
    List<ParkingActorEntity> findByCppId(long cppId);
    
}
 
package net.cis.service;

import java.util.List;

import net.cis.dto.ParkingActorDto;

/**
 * Created by Vincent 23/05/2018
 */
public interface ParkingActorService {

    List<ParkingActorDto> findByActors(long actorId);
    
}
 
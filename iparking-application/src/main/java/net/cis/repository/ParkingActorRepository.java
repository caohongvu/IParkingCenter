package net.cis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.ParkingActorEntity;

/**
 * Created by Vincent 02/10/2018
 */
public interface ParkingActorRepository  extends JpaRepository<ParkingActorEntity, Long> {
	
	List<ParkingActorEntity> findByActor(long actorId);

}

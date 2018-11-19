package net.cis.repository.iparking.center;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.TicketEntity;

/**
 * Created by Vincent 02/10/2018
 */

public interface TicketRepository  extends JpaRepository<TicketEntity, Long> {
	
}

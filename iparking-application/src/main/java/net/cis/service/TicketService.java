package net.cis.service;

import java.util.List;

import net.cis.dto.TicketDto;

/**
 * Created by Vincent 15/11/2018
 */
public interface TicketService {

    TicketDto save(TicketDto ticketDto);
    
    TicketDto findById(long id);

    void delete(TicketDto ticketDto);
    
    List<TicketDto> findAll();

}
 
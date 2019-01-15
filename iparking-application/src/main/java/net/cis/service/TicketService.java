package net.cis.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import net.cis.dto.TicketDto;
import net.cis.dto.TicketPriceDto;
import net.cis.jpa.criteria.TicketCriteria;
import net.cis.jpa.entity.TicketEntity;

/**
 * Created by Vincent 15/11/2018
 */
public interface TicketService {

    TicketDto save(TicketDto ticketDto);
    
    TicketDto findById(long id);

    void delete(TicketDto ticketDto);
    
    List<TicketDto> findAll(TicketCriteria ticketCriteria, Pageable pageable);
    
    TicketPriceDto getPrice(String ticketId, int min);
    
    List<TicketEntity> getByParkingPlace(Long parkingPlace);

}
 
package net.cis.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import net.cis.dto.MonthlyTicketDto;
import net.cis.jpa.criteria.MonthlyTicketCriteria;

/**
 * Created by Vincent 15/11/2018
 */
public interface MonthlyTicketService {

   
    List<MonthlyTicketDto> findAll(MonthlyTicketCriteria ticketCriteria, Pageable pageable);
    
    MonthlyTicketDto findOne(long id);

}
 
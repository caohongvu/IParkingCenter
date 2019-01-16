package net.cis.service;

import org.springframework.data.domain.Pageable;

import net.cis.dto.ResponseApi;
import net.cis.jpa.criteria.TicketDailyCriteria;

public interface TicketDailyPortalService {
    ResponseApi getAllTicketDailyFooterPortal(TicketDailyCriteria ticketCriteria, Pageable pageable);

}

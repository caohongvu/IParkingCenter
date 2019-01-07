package net.cis.service;

import org.springframework.data.domain.Pageable;

import net.cis.dto.ResponseApi;
import net.cis.jpa.criteria.DailyTicketPaymentCriteria;

public interface DailyTicketPaymentService {
    ResponseApi findAllFooter(DailyTicketPaymentCriteria ticketCriteria, Pageable pageable);

}

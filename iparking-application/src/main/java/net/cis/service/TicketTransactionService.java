package net.cis.service;

import java.util.List;


import net.cis.dto.ResponseApi;
import net.cis.dto.TicketTransactionDto;
import net.cis.dto.UpdateInvoiceDto;

/**
 * Created by Vincent 15/11/2018
 */
public interface TicketTransactionService {

    TicketTransactionDto save(TicketTransactionDto ticketTransactionDto);
    
    TicketTransactionDto findById(String id);
    
    TicketTransactionDto findByPaymentOrderNo(String paymentOrderNo);

    void delete(TicketTransactionDto ticketTransactionDto);
    
    List<TicketTransactionDto> findAll();

	ResponseApi findByTicketId(Long ticketId);
	
	ResponseApi getDetailPortal(String id);
	
	ResponseApi updateInvoice(UpdateInvoiceDto dto);

}
 
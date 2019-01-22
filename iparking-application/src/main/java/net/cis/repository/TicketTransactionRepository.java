package net.cis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.TicketTransactionEntity;
import net.cis.jpa.entity.TicketTransactionPortalEntity;

/**
 * Created by Vincent 02/10/2018
 */

public interface TicketTransactionRepository  extends JpaRepository<TicketTransactionEntity, String> {
	
	TicketTransactionEntity findByPaymentOrderNo(String paymentOrderNo);
	List<TicketTransactionEntity> findByTicketId(long ticketId);
	
	TicketTransactionPortalEntity getDetailPortalById(String id);
	
	
	TicketTransactionEntity findByIdAndTicketId(String id, Long ticketId);
	
}

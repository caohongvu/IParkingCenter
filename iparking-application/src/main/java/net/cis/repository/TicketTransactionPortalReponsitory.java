package net.cis.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import net.cis.jpa.entity.TicketTransactionPortalEntity;

public interface TicketTransactionPortalReponsitory extends JpaRepository<TicketTransactionPortalEntity, String> {

	TicketTransactionPortalEntity getDetailPortalById(String id);
	
}
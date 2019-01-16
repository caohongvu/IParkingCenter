package net.cis.service;

import java.util.List;

/**
 * Created by NhanNguyen
 */
public interface InvoiceCenterService {

	List<String> getInvoiceCode(Long ticketId) throws Exception;
	
	

}

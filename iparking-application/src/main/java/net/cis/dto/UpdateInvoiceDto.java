package net.cis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by NhanNguyen on 02/10/2018
 */
public class UpdateInvoiceDto {

	@JsonProperty("TicketId")
	private Long ticketId;

	@JsonProperty("TransactionId")
	private String transactionId;

	@JsonProperty("InvoiceCode")
	private String invoiceCode;

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}	

}

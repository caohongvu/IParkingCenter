package net.cis.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Vincent on 02/10/2018
 */
@Entity
@Table(name = "ticket_transactions")
public class TicketTransactionEntity {
	@Id
    @Column(name="id")
	private String id;
	
	@Column(name="ticket_id")
	private long ticketId;
	
	@Column(name="customer")
	private long customer;
	
	@Column(name="payment_method")
	private String paymentMethod;
	
	@Column(name="is_delegate")
	private int isDelegate;
	
	@Column(name="payment_order_no")
	private String paymentOrderNo;
	
	@Column(name="payment_order_type")
	private String ticketType;
	
	@Column(name="transaction_type")
	private String transactionType;
	
	@Column(name="payment_trans_no")
	private String paymentTransNo;
	
	@Column(name="cus_token")
	private String cusToken;
	
	@Column(name="cus_token_scheme")
	private String cusTokenScheme;
	
	@Column(name="created_at")
	private String createdAt;
	
	@Column(name="updated_at")
	private String updatedAt;
	
	@Column(name="cash_holder")
	private String cashHolder;
	
	@Column(name="payment_amount")
	private String paymentAmount;
	
	@Column(name="apply_from_time")
	private String applyFromTime;
	
	@Column(name="apply_to_time")
	private String applyToTime;
	
	@Column(name="payment_status")
	private int paymentStatus;

	@Column(name="auto_extend")
	private int autoExtend;
	
	@Column(name="invoice_code")
	private String invoiceCode;
	
	
	
	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getTicketId() {
		return ticketId;
	}

	public void setTicketId(long ticketId) {
		this.ticketId = ticketId;
	}

	public long getCustomer() {
		return customer;
	}

	public void setCustomer(long customer) {
		this.customer = customer;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public int getIsDelegate() {
		return isDelegate;
	}

	public void setIsDelegate(int isDelegate) {
		this.isDelegate = isDelegate;
	}

	public String getPaymentOrderNo() {
		return paymentOrderNo;
	}

	public void setPaymentOrderNo(String paymentOrderNo) {
		this.paymentOrderNo = paymentOrderNo;
	}

	public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getPaymentTransNo() {
		return paymentTransNo;
	}

	public void setPaymentTransNo(String paymentTransNo) {
		this.paymentTransNo = paymentTransNo;
	}

	public String getCusTokenScheme() {
		return cusTokenScheme;
	}

	public void setCusTokenScheme(String cusTokenScheme) {
		this.cusTokenScheme = cusTokenScheme;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getCashHolder() {
		return cashHolder;
	}

	public void setCashHolder(String cashHolder) {
		this.cashHolder = cashHolder;
	}

	public String getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getApplyFromTime() {
		return applyFromTime;
	}

	public void setApplyFromTime(String applyFromTime) {
		this.applyFromTime = applyFromTime;
	}

	public String getApplyToTime() {
		return applyToTime;
	}

	public void setApplyToTime(String applyToTime) {
		this.applyToTime = applyToTime;
	}

	public int getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(int paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getCusToken() {
		return cusToken;
	}

	public void setCusToken(String cusToken) {
		this.cusToken = cusToken;
	}

	public int getAutoExtend() {
		return autoExtend;
	}

	public void setAutoExtend(int autoExtend) {
		this.autoExtend = autoExtend;
	}
	
	
}

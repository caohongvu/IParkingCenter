package net.cis.dto;

/**
 * Created by Vincent on 02/10/2018
 */
public class TicketTransactionDto {
	private String id;
	
	private long ticketId;
	
	private long customer;
	
	private String paymentMethod;
	
	private int isDelegate;
	
	private String paymentOrderNo;
	
	private String ticketType;
	
	private String transactionType;
	
	private String paymentTransNo;
	
	private String cusTokenScheme;
	
	private String createdAt;
	
	private String updatedAt;
	
	private String cashHolder;
	
	private double paymentAmount;
	
	private String applyFromTime;
	
	private String applyToTime;
	
	private int paymentStatus;

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

	public double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(double paymentAmount) {
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
	
	
}

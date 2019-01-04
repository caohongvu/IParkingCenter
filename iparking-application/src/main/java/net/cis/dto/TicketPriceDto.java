package net.cis.dto;

/**
 * Created by Vincent on 02/10/2018
 */
public class TicketPriceDto {

	private long id;
	
	private long ticketId;
	private String orderId;
	private long cusId;
	private long cusToken;
	private String paymentRequestAt;
	private String paymentRequestEndAt;
	private double paymentAmount;
	private int paymentStatus;
	private String applyFromTime;
	private String applyToTime;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getTicketId() {
		return ticketId;
	}
	public void setTicketId(long ticketId) {
		this.ticketId = ticketId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public long getCusId() {
		return cusId;
	}
	public void setCusId(long cusId) {
		this.cusId = cusId;
	}
	public long getCusToken() {
		return cusToken;
	}
	public void setCusToken(long cusToken) {
		this.cusToken = cusToken;
	}
	public String getPaymentRequestAt() {
		return paymentRequestAt;
	}
	public void setPaymentRequestAt(String paymentRequestAt) {
		this.paymentRequestAt = paymentRequestAt;
	}
	public String getPaymentRequestEndAt() {
		return paymentRequestEndAt;
	}
	public void setPaymentRequestEndAt(String paymentRequestEndAt) {
		this.paymentRequestEndAt = paymentRequestEndAt;
	}
	public double getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public int getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(int paymentStatus) {
		this.paymentStatus = paymentStatus;
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
	
	
	
}

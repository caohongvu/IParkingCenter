package net.cis.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MonthlyInvoiceDto {
	@JsonProperty("TicketID")
	private long ticketId;
	
	@JsonProperty("TransactionAmount")
	private double transactionAmount;
	
	@JsonProperty("CarppID")
	private String cppId;
	
	@JsonProperty("ProviderID")
	private long providerId;
	
	@JsonProperty("BuyerName")
	private String buyerName;
	
	@JsonProperty("BuyerTaxCode")
	private String buyerTaxCode;
	
	@JsonProperty("BuyerUnitName")
	private String buyerUnitName;
	
	@JsonProperty("BuyerAddress")
	private String buyerAddress;
	
	@JsonProperty("BuyerBankAccount")
	private String buyerBankAccount;
	
	@JsonProperty("ReceiverEmail")
	private String receiverEmail;
	
	@JsonProperty("ReceiverMobile")
	private String receiverMobile;
	
	@JsonProperty("ReceiverAddress")
	private String receiverAddress;
	
	@JsonProperty("ReceiverName")
	private String receiverName;
	
	@JsonProperty("CppCode")
	private String cppCode;
	
	@JsonProperty("TransactionID")
	private String transactionId;
	
	@JsonProperty("IsMonthly")
	private int isMonthly;
	
	@JsonProperty("PaymentConfiguration")
	private List<PaymentConfigDto> paymentConfiguration;
	
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getBuyerTaxCode() {
		return buyerTaxCode;
	}
	public void setBuyerTaxCode(String buyerTaxCode) {
		this.buyerTaxCode = buyerTaxCode;
	}
	public String getBuyerUnitName() {
		return buyerUnitName;
	}
	public void setBuyerUnitName(String buyerUnitName) {
		this.buyerUnitName = buyerUnitName;
	}
	public String getBuyerAddress() {
		return buyerAddress;
	}
	public void setBuyerAddress(String buyerAddress) {
		this.buyerAddress = buyerAddress;
	}
	public String getBuyerBankAccount() {
		return buyerBankAccount;
	}
	public void setBuyerBankAccount(String buyerBankAccount) {
		this.buyerBankAccount = buyerBankAccount;
	}
	public String getReceiverEmail() {
		return receiverEmail;
	}
	public void setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
	}
	public String getReceiverMobile() {
		return receiverMobile;
	}
	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}
	public String getReceiverAddress() {
		return receiverAddress;
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public long getTicketId() {
		return ticketId;
	}
	public void setTicketId(long ticketId) {
		this.ticketId = ticketId;
	}
	public double getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public String getCppId() {
		return cppId;
	}
	public void setCppId(String cppId) {
		this.cppId = cppId;
	}
	public long getProviderId() {
		return providerId;
	}
	public void setProviderId(long providerId) {
		this.providerId = providerId;
	}
	public String getCppCode() {
		return cppCode;
	}
	public void setCppCode(String cppCode) {
		this.cppCode = cppCode;
	}
	public List<PaymentConfigDto> getPaymentConfiguration() {
		return paymentConfiguration;
	}
	public void setPaymentConfiguration(List<PaymentConfigDto> paymentConfiguration) {
		this.paymentConfiguration = paymentConfiguration;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public int getIsMonthly() {
		return isMonthly;
	}
	public void setIsMonthly(int isMonthly) {
		this.isMonthly = isMonthly;
	}
}

package net.cis.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReportProportionPaymentDto {
	private String id;
	@JsonProperty("code")
	private String parkingCode;
	@JsonProperty("company")
	private String company;
	@JsonInclude(Include.NON_NULL)
	private Double revenue;
	@JsonInclude(Include.NON_NULL)
	private Double atm;
	@JsonInclude(Include.NON_NULL)
	private Double sms;
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("balance")
	private Double balance;
	@JsonInclude(Include.NON_NULL)
	private Double balance_tth;
	@JsonInclude(Include.NON_NULL)
	private Double visa_master_kh;
	@JsonInclude(Include.NON_NULL)
	private Double visa_master_tth;
	@JsonInclude(Include.NON_NULL)
	private Double cash;

	// them field gui ve client
	@JsonProperty("card")
	@JsonInclude(Include.NON_NULL)
	private Double card;
	@JsonProperty("delegate_payment")
	@JsonInclude(Include.NON_NULL)
	private Double delegatePayment;

	@JsonProperty("cpp_id")
	private long parkingId;
	@JsonProperty("company_id")
	private long companyId;
	@JsonProperty("address")
	private String address;
	@JsonProperty("capacity")
	private int capacity;

	public Double getCard() {
		return card;
	}

	public void setCard(Double card) {
		this.card = card;
	}

	public Double getDelegatePayment() {
		return delegatePayment;
	}

	public void setDelegatePayment(Double delegatePayment) {
		this.delegatePayment = delegatePayment;
	}

	public ReportProportionPaymentDto() {
		super();
	}

	public ReportProportionPaymentDto(String id, String parkingCode, String company, Double revenue, Double atm,
			Double sms, Double balance, Double balance_tth, Double visa_master_kh, Double visa_master_tth, Double cash,
			long parkingId, long companyId, String address, int capacity) {
		super();
		this.id = id;
		this.parkingCode = parkingCode;
		this.company = company;
		this.revenue = revenue;
		this.atm = atm;
		this.sms = sms;
		this.balance = balance;
		this.balance_tth = balance_tth;
		this.visa_master_kh = visa_master_kh;
		this.visa_master_tth = visa_master_tth;
		this.cash = cash;
		this.parkingId = parkingId;
		this.companyId = companyId;
		this.address = address;
		this.capacity = capacity;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getRevenue() {
		return revenue;
	}

	public void setRevenue(Double revenue) {
		this.revenue = revenue;
	}

	public Double getAtm() {
		return atm;
	}

	public void setAtm(Double atm) {
		this.atm = atm;
	}

	public Double getSms() {
		return sms;
	}

	public void setSms(Double sms) {
		this.sms = sms;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getBalance_tth() {
		return balance_tth;
	}

	public void setBalance_tth(Double balance_tth) {
		this.balance_tth = balance_tth;
	}

	public Double getVisa_master_kh() {
		return visa_master_kh;
	}

	public void setVisa_master_kh(Double visa_master_kh) {
		this.visa_master_kh = visa_master_kh;
	}

	public Double getVisa_master_tth() {
		return visa_master_tth;
	}

	public void setVisa_master_tth(Double visa_master_tth) {
		this.visa_master_tth = visa_master_tth;
	}

	public Double getCash() {
		return cash;
	}

	public void setCash(Double cash) {
		this.cash = cash;
	}

	public String getParkingCode() {
		return parkingCode;
	}

	public void setParkingCode(String parkingCode) {
		this.parkingCode = parkingCode;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public long getParkingId() {
		return parkingId;
	}

	public void setParkingId(long parkingId) {
		this.parkingId = parkingId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

}

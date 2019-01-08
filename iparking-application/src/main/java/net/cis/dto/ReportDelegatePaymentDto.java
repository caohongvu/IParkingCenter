package net.cis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Vincent on 02/10/2018
 */
public class ReportDelegatePaymentDto {
	
	@JsonProperty("CppID")
	private long carppId;
	
	@JsonProperty("Code")
	private String code;
	
	@JsonProperty("Address")
	private String address;
	
	@JsonProperty("Amount")
	private long amount;
	
	@JsonProperty("Date")
	private String createdDate;
	
	public long getCarppId() {
		return carppId;
	}
	public void setCarppId(long carppId) {
		this.carppId = carppId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
	
}

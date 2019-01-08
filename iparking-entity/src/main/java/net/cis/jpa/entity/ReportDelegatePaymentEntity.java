package net.cis.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Vincent on 02/10/2018
 */
@Entity
@Table(name="delegate_payment_view")
public class ReportDelegatePaymentEntity {
	
	@Id
    @Column(name="parking_place")
	private long carppId;
	
	@Column(name="parking_code")
	private String code;
	
	@Column(name="address")
	private String address;
	
	@Column(name="amount")
	private long amount;
	
	@Column(name="created_at")
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

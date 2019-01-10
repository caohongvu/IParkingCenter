package net.cis.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author liemnh
 *
 */
@Entity
@Table(name = "proportion_payment_view")
public class ProportionPaymentEntity {
	@Id
	@Column(name = "id_uuid")
	private String id;

	@Column(name = "parking_code")
	private String code;

	@Column(name = "cpp_id")
	private long cppId;

	@Column(name = "address")
	private String address;

	@Column(name = "capacity")
	private String capacity;

	@Column(name = "revenue")
	private double revenue;

	@Column(name = "atm")
	private double atm;

	@Column(name = "sms")
	private double sms;

	@Column(name = "balance")
	private double balance;

	@Column(name = "balance_tth")
	private double balance_tth;

	@Column(name = "visa_master_kh")
	private double visa_master_kh;

	@Column(name = "visa_master_tth")
	private double visa_master_tth;

	@Column(name = "cash")
	private double cash;

	@Column(name = "provider_id")
	private int provider_id;

	@Column(name = "day")
	private String day;

	public long getCppId() {
		return cppId;
	}

	public void setCppId(long cppId) {
		this.cppId = cppId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public double getRevenue() {
		return revenue;
	}

	public void setRevenue(double revenue) {
		this.revenue = revenue;
	}

	public double getAtm() {
		return atm;
	}

	public void setAtm(double atm) {
		this.atm = atm;
	}

	public double getSms() {
		return sms;
	}

	public void setSms(double sms) {
		this.sms = sms;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getBalance_tth() {
		return balance_tth;
	}

	public void setBalance_tth(double balance_tth) {
		this.balance_tth = balance_tth;
	}

	public double getVisa_master_kh() {
		return visa_master_kh;
	}

	public void setVisa_master_kh(double visa_master_kh) {
		this.visa_master_kh = visa_master_kh;
	}

	public double getVisa_master_tth() {
		return visa_master_tth;
	}

	public void setVisa_master_tth(double visa_master_tth) {
		this.visa_master_tth = visa_master_tth;
	}

	public double getCash() {
		return cash;
	}

	public void setCash(double cash) {
		this.cash = cash;
	}

	public int getProvider_id() {
		return provider_id;
	}

	public void setProvider_id(int provider_id) {
		this.provider_id = provider_id;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

}

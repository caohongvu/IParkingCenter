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
@Table(name = "report_proportion_payment")
public class ReportProportionPaymentEntity {
	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "parking_code")
	private String parkingCode;

	@Column(name = "company")
	private String company;

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

	@Column(name = "parking_id")
	private long parkingId;

	@Column(name = "company_id")
	private long companyId;

	@Column(name = "day")
	private long day;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public long getDay() {
		return day;
	}

	public void setDay(long day) {
		this.day = day;
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

}

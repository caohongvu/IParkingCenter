package net.cis.dto;

public class ReportProportionPaymentDto {
	private String id;
	private String parkingCode;
	private String company;
	private double revenue;
	private double atm;
	private double sms;
	private double balance;
	private double balance_tth;
	private double visa_master_kh;
	private double visa_master_tth;
	private double cash;
	private long parkingId;
	private long companyId;

	private String address;
	private int capacity;

	public ReportProportionPaymentDto() {
		super();
	}

	public ReportProportionPaymentDto(String id, String parkingCode, String company, double revenue, double atm,
			double sms, double balance, double balance_tth, double visa_master_kh, double visa_master_tth, double cash,
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

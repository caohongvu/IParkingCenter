package net.cis.dto;

public class ReportMonthlyPaymentDto {
	private long id;

	private String parkingCode;

	private String company;

	private Double revenue;

	private long day;

	private long parkingId;

	private long companyId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Double getRevenue() {
		return revenue;
	}

	public void setRevenue(Double revenue) {
		this.revenue = revenue;
	}

	public long getDay() {
		return day;
	}

	public void setDay(long day) {
		this.day = day;
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

package net.cis.dto;

public class MonthlyTicketRevenueDto {
	private String code;
	private double revenue;

	public MonthlyTicketRevenueDto() {
		super();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getRevenue() {
		return revenue;
	}

	public void setRevenue(double revenue) {
		this.revenue = revenue;
	}

	public MonthlyTicketRevenueDto(String code, double revenue) {
		super();
		this.code = code;
		this.revenue = revenue;
	}
}

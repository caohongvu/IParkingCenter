package net.cis.dto;

public class DailyTicketRevenueDto {
	private String code;

	private double revenue;

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

	public DailyTicketRevenueDto(String code, double revenue) {
		super();
		this.code = code;
		this.revenue = revenue;
	}

}

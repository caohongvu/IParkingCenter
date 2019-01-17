package net.cis.dto;

import java.util.List;

public class MonthlyTicketReportEndPointDto {
	private long total_row;
	private long countCpp_code;
	private long countPayment_code;
	private long countPhone;
	private double totalTicket_amount;
	private double totalPayment_amount;
	private List<MonthlyTicketReportDto> ticketReportDtos;
	public long getTotal_row() {
		return total_row;
	}
	public void setTotal_row(long total_row) {
		this.total_row = total_row;
	}
	public long getCountCpp_code() {
		return countCpp_code;
	}
	public void setCountCpp_code(long countCpp_code) {
		this.countCpp_code = countCpp_code;
	}
	public long getCountPayment_code() {
		return countPayment_code;
	}
	public void setCountPayment_code(long countPayment_code) {
		this.countPayment_code = countPayment_code;
	}
	public long getCountPhone() {
		return countPhone;
	}
	public void setCountPhone(long countPhone) {
		this.countPhone = countPhone;
	}
	public double getTotalTicket_amount() {
		return totalTicket_amount;
	}
	public void setTotalTicket_amount(double totalTicket_amount) {
		this.totalTicket_amount = totalTicket_amount;
	}
	public double getTotalPayment_amount() {
		return totalPayment_amount;
	}
	public void setTotalPayment_amount(double totalPayment_amount) {
		this.totalPayment_amount = totalPayment_amount;
	}
	public List<MonthlyTicketReportDto> getTicketReportDtos() {
		return ticketReportDtos;
	}
	public void setTicketReportDtos(List<MonthlyTicketReportDto> ticketReportDtos) {
		this.ticketReportDtos = ticketReportDtos;
	}
	
	
}

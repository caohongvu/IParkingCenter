package net.cis.dto;

import java.util.List;

public class MonthlyTicketPaymentEndPointDto {
	private long total_row;
	private long countCpp_code;
	private long countContractNo;
	private long countContracCode;
	private long countPhone;
	private double totalPayment_amount;
	private List<MonthlyTicketPaymentDto> monthlyTicketPayment;
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
	public long getCountContractNo() {
		return countContractNo;
	}
	public void setCountContractNo(long countContractNo) {
		this.countContractNo = countContractNo;
	}
	public long getCountContracCode() {
		return countContracCode;
	}
	public void setCountContracCode(long countContracCode) {
		this.countContracCode = countContracCode;
	}
	public long getCountPhone() {
		return countPhone;
	}
	public void setCountPhone(long countPhone) {
		this.countPhone = countPhone;
	}
	public double getTotalPayment_amount() {
		return totalPayment_amount;
	}
	public void setTotalPayment_amount(double totalPayment_amount) {
		this.totalPayment_amount = totalPayment_amount;
	}
	public List<MonthlyTicketPaymentDto> getMonthlyTicketPayment() {
		return monthlyTicketPayment;
	}
	public void setMonthlyTicketPayment(List<MonthlyTicketPaymentDto> monthlyTicketPayment) {
		this.monthlyTicketPayment = monthlyTicketPayment;
	}
	
}

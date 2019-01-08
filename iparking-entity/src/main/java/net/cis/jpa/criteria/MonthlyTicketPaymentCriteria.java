package net.cis.jpa.criteria;

public class MonthlyTicketPaymentCriteria {
	private String transId;
	private String cppCode;
	private String numberplate;
	private String contract_no;
	private String contract_code;
	private Long phone;
	private String start_time;
	private String end_time;
	private String cardNumber;
	private String periodPayment;
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	public String getCppCode() {
		return cppCode;
	}
	public void setCppCode(String cppCode) {
		this.cppCode = cppCode;
	}
	public String getNumberplate() {
		return numberplate;
	}
	public void setNumberplate(String numberplate) {
		this.numberplate = numberplate;
	}
	public String getContract_no() {
		return contract_no;
	}
	public void setContract_no(String contract_no) {
		this.contract_no = contract_no;
	}
	public String getContract_code() {
		return contract_code;
	}
	public void setContract_code(String contract_code) {
		this.contract_code = contract_code;
	}
	public Long getPhone() {
		return phone;
	}
	public void setPhone(Long phone) {
		this.phone = phone;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getPeriodPayment() {
		return periodPayment;
	}
	public void setPeriodPayment(String periodPayment) {
		this.periodPayment = periodPayment;
	}
	
}

package net.cis.dto;



public class MonthlyTicketPaymentDto {
	private Long ticket_id;
	private String transaction_id;
	private String contract_code;
	private String contract_no;
	private String parking_place;
	private String number_plate;
	private String payment_order_no;
	private String payment_trans_no;
	private String payment_method;
	private double payment_amount;
	private String created_at;
	private Long phone;
	private String card_number;
	private String apply_from_time;
	private String apply_to_time;
	private String periodPayment;
	
	public Long getTicket_id() {
		return ticket_id;
	}
	public void setTicket_id(Long ticket_id) {
		this.ticket_id = ticket_id;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getContract_code() {
		return contract_code;
	}
	public void setContract_code(String contract_code) {
		this.contract_code = contract_code;
	}
	public String getContract_no() {
		return contract_no;
	}
	public void setContract_no(String contract_no) {
		this.contract_no = contract_no;
	}
	public String getParking_place() {
		return parking_place;
	}
	public void setParking_place(String parking_place) {
		this.parking_place = parking_place;
	}
	public String getNumber_plate() {
		return number_plate;
	}
	public void setNumber_plate(String number_plate) {
		this.number_plate = number_plate;
	}
	public String getPayment_order_no() {
		return payment_order_no;
	}
	public void setPayment_order_no(String payment_order_no) {
		this.payment_order_no = payment_order_no;
	}
	public String getPayment_trans_no() {
		return payment_trans_no;
	}
	public void setPayment_trans_no(String payment_trans_no) {
		this.payment_trans_no = payment_trans_no;
	}
	public String getPayment_method() {
		return payment_method;
	}
	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}
	public double getPayment_amount() {
		return payment_amount;
	}
	public void setPayment_amount(double payment_amount) {
		this.payment_amount = payment_amount;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public Long getPhone() {
		return phone;
	}
	public void setPhone(Long phone) {
		this.phone = phone;
	}
	public String getCard_number() {
		return card_number;
	}
	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}
	public String getApply_from_time() {
		return apply_from_time;
	}
	public void setApply_from_time(String apply_from_time) {
		this.apply_from_time = apply_from_time;
	}
	public String getApply_to_time() {
		return apply_to_time;
	}
	public void setApply_to_time(String apply_to_time) {
		this.apply_to_time = apply_to_time;
	}
	public String getPeriodPayment() {
		return periodPayment;
	}
	public void setPeriodPayment(String periodPayment) {
		this.periodPayment = periodPayment;
	}
	
}

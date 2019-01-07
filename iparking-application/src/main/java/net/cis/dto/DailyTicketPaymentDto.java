package net.cis.dto;

import java.util.Date;



public class DailyTicketPaymentDto {
	private long id;	
	private String transaction_id;
	private String parking_code;
	private String cppCode;
	private String payment_order_no;
	private String payment_trans_no;
	private String payment_method;
	private String car_number_plate;
	private double payment_amount;
	private boolean is_delegate;
	private String transaction_type;
	private Date created_at;
	private String start_time;
	private String end_time;
	private Long phone;
	private String card_number;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getParking_code() {
		return parking_code;
	}
	public void setParking_code(String parking_code) {
		this.parking_code = parking_code;
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
	public String getCar_number_plate() {
		return car_number_plate;
	}
	public void setCar_number_plate(String car_number_plate) {
		this.car_number_plate = car_number_plate;
	}
	public double getPayment_amount() {
		return payment_amount;
	}
	public void setPayment_amount(double payment_amount) {
		this.payment_amount = payment_amount;
	}
	public boolean isIs_delegate() {
		return is_delegate;
	}
	public void setIs_delegate(boolean is_delegate) {
		this.is_delegate = is_delegate;
	}
	public String getTransaction_type() {
		return transaction_type;
	}
	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
	}
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
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
	public String getCppCode() {
		return cppCode;
	}
	public void setCppCode(String cppCode) {
		this.cppCode = cppCode;
	}
	
	
	
}

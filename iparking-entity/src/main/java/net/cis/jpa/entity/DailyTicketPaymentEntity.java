package net.cis.jpa.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="daily_ticket_payment_view")
public class DailyTicketPaymentEntity {

	@Column(name="ticket_id")
	private long ticket_id;

	@Id
	@Column(name="transaction_id")
	private String transaction_id;
	
	@Column(name="parking_code")
	private String parking_code;
	
	@Column(name="payment_order_no")
	private String payment_order_no;
	
	@Column(name="payment_trans_no")
	private String payment_trans_no;
	
	@Column(name="payment_method")
	private String payment_method;
	
	@Column(name="car_number_plate")
	private String car_number_plate;
	
	@Column(name="payment_amount")
	private double payment_amount;
	
	@Column(name="is_delegate")
	private boolean is_delegate;
	
	@Column(name="transaction_type")
	private String transaction_type;
	
	@Column(name="created_at")
	private Date created_at;
	
	@Column(name="start_time")
	private String start_time;
	
	@Column(name="end_time")
	private String end_time;
	
	@Column(name="phone2")
	private Long phone;
	
	@Column(name="card_number")
	private String card_number;
	
	@Column(name="invoice_code")
	private String invoice_code;
	

	public long getTicket_id() {
		return ticket_id;
	}

	public void setTicket_id(long ticket_id) {
		this.ticket_id = ticket_id;
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

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getCard_number() {
		return card_number;
	}

	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}

	public String getInvoice_code() {
		return invoice_code;
	}

	public void setInvoice_code(String invoice_code) {
		this.invoice_code = invoice_code;
	}
	
	


}

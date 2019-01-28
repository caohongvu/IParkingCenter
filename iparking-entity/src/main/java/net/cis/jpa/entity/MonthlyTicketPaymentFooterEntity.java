package net.cis.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

@Entity
@Table(name = "monthly_ticket_payment_view")
public class MonthlyTicketPaymentFooterEntity {
	@Id
    @Column(name = "id")
	private String id; // The row number!
	
	@Column(name="ticket_id")
	private Long ticket_id;
	
	@Column(name="transaction_id")
	private String transaction_id;
	
	@Column(name="contract_code")
	private String contract_code;
	
	@Column(name="contract_no")
	private String contract_no;

	@Column(name="parking_place")
	private String parking_place;
	
	@Column(name="number_plate")
	private String number_plate;
	
	@Column(name="payment_order_no")
	private String payment_order_no;
	
	@Column(name="payment_trans_no")
	private String payment_trans_no;
	
	@Column(name="payment_method")
	private String payment_method;
	
	@Column(name="payment_amount")
	private double payment_amount;
	
	@Column(name="created_at")
	private String created_at;
	
	@Column(name="phone2")
	private Long phone;
	
	@Column(name="card_number")
	private String card_number;
	
	@Column(name="apply_from_time")
	private String apply_from_time;
	
	@Column(name="apply_to_time")
	private String apply_to_time;
	
	@Formula("COUNT(DISTINCT transaction_id)")
	private long total_row;
	
	@Formula("COUNT(DISTINCT parking_place)")
	private long countCpp_code;
	
	@Formula("COUNT(DISTINCT contract_no)")
	private long countContractNo;
	
	@Formula("COUNT(DISTINCT contract_code)")
	private long countContracCode;
	
	@Formula("COUNT(DISTINCT phone2)")
	private long countPhone;
	
	@Formula("SUM(payment_amount)")
	private double totalPayment_amount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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
	
	
	
}

package net.cis.jpa.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

@Entity
@Table(name = "report_monthly_ticket_view")
public class MonthlyTicketReportFooterEntity {

	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "ticket_id")
	private Long ticket_id;
	
	@Column(name = "contract_code")
	private String contract_code;
	
	@Column(name = "contract_no")
	private String contract_no;
	
	@Column(name = "parking_place")
	private String parking_place;
	
	@Column(name = "number_plate")
	private String number_plate;
	
	@Column(name = "phone2")
	private Long phone;
	
	@Column(name = "total_amount")
	private double total_amount;
	
	@Column(name = "paid_amount")
	private double paid_amount;
	
	@Column(name = "is_paid")
	private boolean is_paid;
	
	@Column(name = "duration")
	private int duration;
	
	@Column(name = "valid_from")
	private Long valid_from;
	
	@Column(name = "valid_to")
	private Long valid_to;
	
	@Column(name = "valid_end")
	private Long valid_end;
	
	@Column(name = "expired")
	private int expired;

	@Column(name = "fullname")
	private String fullName;
	
	@Column(name = "status")
	private int status;
	
	@Column(name = "is_liquidated")
	private boolean is_liquidated;
	
	@Column(name = "created_at")
	private Date created_at;
	
	@Formula("COUNT(*)")
	private long total_row;
	@Formula("COUNT(DISTINCT parking_place)")
	private long countCpp_code;
	@Formula("COUNT(DISTINCT contract_code)")
	private long countPayment_code;
	@Formula("COUNT(DISTINCT phone2)")
	private long countPhone;
	@Formula("SUM(paid_amount)")
	private double totalPayment_amount;
	@Formula("SUM(total_amount)")
	private double totalTicket_amount;
	
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
	public Long getPhone() {
		return phone;
	}
	public void setPhone(Long phone) {
		this.phone = phone;
	}
	public double getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(double total_amount) {
		this.total_amount = total_amount;
	}
	public double getPaid_amount() {
		return paid_amount;
	}
	public void setPaid_amount(double paid_amount) {
		this.paid_amount = paid_amount;
	}
	public boolean isIs_paid() {
		return is_paid;
	}
	public void setIs_paid(boolean is_paid) {
		this.is_paid = is_paid;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public Long getValid_from() {
		return valid_from;
	}
	public void setValid_from(Long valid_from) {
		this.valid_from = valid_from;
	}
	public Long getValid_to() {
		return valid_to;
	}
	public void setValid_to(Long valid_to) {
		this.valid_to = valid_to;
	}
	public Long getValid_end() {
		return valid_end;
	}
	public void setValid_end(Long valid_end) {
		this.valid_end = valid_end;
	}
	public int getExpired() {
		return expired;
	}
	public void setExpired(int expired) {
		this.expired = expired;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
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
	public double getTotalPayment_amount() {
		return totalPayment_amount;
	}
	public void setTotalPayment_amount(double totalPayment_amount) {
		this.totalPayment_amount = totalPayment_amount;
	}
	public double getTotalTicket_amount() {
		return totalTicket_amount;
	}
	public void setTotalTicket_amount(double totalTicket_amount) {
		this.totalTicket_amount = totalTicket_amount;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public boolean isIs_liquidated() {
		return is_liquidated;
	}
	public void setIs_liquidated(boolean is_liquidated) {
		this.is_liquidated = is_liquidated;
	}
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	
	
	
	
	
	
}

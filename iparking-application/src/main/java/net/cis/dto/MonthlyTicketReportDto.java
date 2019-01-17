package net.cis.dto;



public class MonthlyTicketReportDto {
	private Long ticket_id;
	private String contract_code;
	private String contract_no;
	private String parking_place;
	private String number_plate;
	private Long phone;
	private double total_amount;
	private double paid_amount;
	private boolean is_paid;
	private int duration;
	private Long valid_from;
	private Long valid_to;
	private Long valid_end;
	private int expired;
	private String fullName;
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
	
	
}

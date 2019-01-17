package net.cis.jpa.criteria;

public class MonthlyTicketReportCriteria {
	private String parking_place;
	private String contract_code;
	private String contract_no;
	private String number_plate;
	private Long phone;
	private String fullName;
	private Long valid_from;
	private Long valid_end;
	private int expired;
	private int is_paid;
	private int status;
	public String getParking_place() {
		return parking_place;
	}
	public void setParking_place(String parking_place) {
		this.parking_place = parking_place;
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
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Long getValid_from() {
		return valid_from;
	}
	public void setValid_from(Long valid_from) {
		this.valid_from = valid_from;
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
	public int getIs_paid() {
		return is_paid;
	}
	public void setIs_paid(int is_paid) {
		this.is_paid = is_paid;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
	
}

package net.cis.jpa.criteria;

public class TicketDailyCriteria {

	private String cppCode;
	private String numberplate;
	private Long phone2;
	private String start_time;
	private String end_time;
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
	public Long getPhone2() {
		return phone2;
	}
	public void setPhone2(Long phone2) {
		this.phone2 = phone2;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getTo_time() {
		return end_time;
	}
	public void setTo_time(String to_time) {
		this.end_time = to_time;
	}

}

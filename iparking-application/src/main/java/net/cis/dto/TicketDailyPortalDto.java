package net.cis.dto;

import java.util.Date;

public class TicketDailyPortalDto {
	private long id;

	private long parkingPlace;

	private String cppCode;

	private String address;

	private long customer;

	private String car_number_plate;

	private int carType;

	private int carPricingGroup;

	private double paidAmount;

	private double mustPayAmount;

	private String start_time;

	private String end_time;

	private String actualEndTime;

	private int status;

	private boolean inSession;

	private String ticketData;

	private long monthlyTicketId;

	private String createdAt;

	private String updatedAt;
	
	private String companyName;
	
	private long phone2;
	
	private Integer	duration;
	
	private String parking_code;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getParkingPlace() {
		return parkingPlace;
	}

	public void setParkingPlace(long parkingPlace) {
		this.parkingPlace = parkingPlace;
	}

	public String getCppCode() {
		return cppCode;
	}

	public void setCppCode(String cppCode) {
		this.cppCode = cppCode;
	}


	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getCustomer() {
		return customer;
	}

	public void setCustomer(long customer) {
		this.customer = customer;
	}

	
	public String getCar_number_plate() {
		return car_number_plate;
	}

	public void setCar_number_plate(String car_number_plate) {
		this.car_number_plate = car_number_plate;
	}



	public String getParking_code() {
		return parking_code;
	}

	public void setParking_code(String parking_code) {
		this.parking_code = parking_code;
	}

	public int getCarType() {
		return carType;
	}

	public void setCarType(int carType) {
		this.carType = carType;
	}

	public int getCarPricingGroup() {
		return carPricingGroup;
	}

	public void setCarPricingGroup(int carPricingGroup) {
		this.carPricingGroup = carPricingGroup;
	}

	public double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public double getMustPayAmount() {
		return mustPayAmount;
	}

	public void setMustPayAmount(double mustPayAmount) {
		this.mustPayAmount = mustPayAmount;
	}

	

	public String getActualEndTime() {
		return actualEndTime;
	}

	public void setActualEndTime(String actualEndTime) {
		this.actualEndTime = actualEndTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isInSession() {
		return inSession;
	}

	public void setInSession(boolean inSession) {
		this.inSession = inSession;
	}

	public String getTicketData() {
		return ticketData;
	}

	public void setTicketData(String ticketData) {
		this.ticketData = ticketData;
	}

	public long getMonthlyTicketId() {
		return monthlyTicketId;
	}

	public void setMonthlyTicketId(long monthlyTicketId) {
		this.monthlyTicketId = monthlyTicketId;
	}


	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public long getPhone2() {
		return phone2;
	}

	public void setPhone2(long phone2) {
		this.phone2 = phone2;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
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

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	
	

	
	

}

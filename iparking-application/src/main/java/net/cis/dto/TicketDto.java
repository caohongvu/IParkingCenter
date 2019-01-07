package net.cis.dto;

import java.util.Date;

/**
 * Created by Vincent on 02/10/2018
 */
public class TicketDto {

	private long id;

	private long parkingPlace;

	private String cppCode;

	private String cppAddress;

	private long customer;

	private String carNumberPlate;

	private int carType;

	private int carPricingGroup;

	private double paidAmount;

	private double mustPayAmount;

	private String startTime;

	private String endTime;

	private String actualEndTime;

	private int status;

	private boolean inSession;

	private String ticketData;

	private long monthlyTicketId;

	private Date createdAt;

	private String updatedAt;

	private String customerPhone;

	private String email;

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

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

	public String getCppAddress() {
		return cppAddress;
	}

	public void setCppAddress(String cppAddress) {
		this.cppAddress = cppAddress;
	}

	public long getCustomer() {
		return customer;
	}

	public void setCustomer(long customer) {
		this.customer = customer;
	}

	public String getCarNumberPlate() {
		return carNumberPlate;
	}

	public void setCarNumberPlate(String carNumberPlate) {
		this.carNumberPlate = carNumberPlate;
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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
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

	public boolean getInSession() {
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

}

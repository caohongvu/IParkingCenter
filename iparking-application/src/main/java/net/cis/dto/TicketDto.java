package net.cis.dto;

/**
 * Created by Vincent on 02/10/2018
 */
public class TicketDto {

	private long id;
	
	private long parkingPlace;
	
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
	
	private String createdAt;
	
	private String updatedAt;

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

	public boolean getIsInSession() {
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

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	
}

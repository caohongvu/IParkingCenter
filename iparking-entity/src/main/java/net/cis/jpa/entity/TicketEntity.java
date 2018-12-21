package net.cis.jpa.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Created by Vincent on 02/10/2018
 */
@Entity
@Table(name = "ticket")
@NamedQuery(name="TicketEntity.findAll", query="SELECT ticket FROM TicketEntity ticket")
public class TicketEntity {
	
	@Id
	private long id;
	
	@Column(name="parking_place")
	private long parkingPlace;
	
	@Column(name="customer")
	private long customer;
	
	@Column(name="car_number_plate")
	private String carNumberPlate;
	
	@Column(name="car_type")
	private int carType;
	
	@Column(name="car_pricing_group")
	private int carPricingGroup;
	
	@Column(name="paid_amount")
	private double paidAmount;
	
	@Column(name="must_pay_amount")
	private double mustPayAmount;
	
	@Column(name="start_time")
	private String startTime;
	
	@Column(name="end_time")
	private String endTime;
	
	@Column(name="actual_end_time")
	private String actualEndTime;
	
	@Column(name="status")
	private int status;
	
	
	@Column(name="in_session")
	private boolean inSession;
	
	@Column(name="ticket_data")
	private String ticketData;
	
	@Column(name="monthly_ticket_id")
	private long monthlyTicketId;
	
	@Column(name="created_at")
	private Date createdAt;
	
	@Column(name="updated_at")
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

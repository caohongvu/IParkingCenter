package net.cis.dto;

import java.util.Date;

public class BillDto {
	private long id;
	private Long parkingPlace;
	private Long customer;
	private String info;
	private Double mustPayAmount;
	private Integer status;
	private PrivateServicesParkingCusDto privateServicesParkingCus;
	private String description;
	private Date sysnDate;
	private Date createdAt;
	private Date updatedAt;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getParkingPlace() {
		return parkingPlace;
	}

	public void setParkingPlace(Long parkingPlace) {
		this.parkingPlace = parkingPlace;
	}

	public Long getCustomer() {
		return customer;
	}

	public void setCustomer(Long customer) {
		this.customer = customer;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Double getMustPayAmount() {
		return mustPayAmount;
	}

	public void setMustPayAmount(Double mustPayAmount) {
		this.mustPayAmount = mustPayAmount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public PrivateServicesParkingCusDto getPrivateServicesParkingCus() {
		return privateServicesParkingCus;
	}

	public void setPrivateServicesParkingCus(PrivateServicesParkingCusDto privateServicesParkingCus) {
		this.privateServicesParkingCus = privateServicesParkingCus;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getSysnDate() {
		return sysnDate;
	}

	public void setSysnDate(Date sysnDate) {
		this.sysnDate = sysnDate;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

}

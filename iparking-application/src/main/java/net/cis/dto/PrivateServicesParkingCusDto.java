package net.cis.dto;

import java.util.Date;

public class PrivateServicesParkingCusDto {
	private long id;
	private Long parkingServiceParkingId;
	private Long cusId;
	private Date createdAt;
	private Date updatedAt;
	private String info;
	private Integer status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getParkingServiceParkingId() {
		return parkingServiceParkingId;
	}

	public void setParkingServiceParkingId(Long parkingServiceParkingId) {
		this.parkingServiceParkingId = parkingServiceParkingId;
	}

	public Long getCusId() {
		return cusId;
	}

	public void setCusId(Long cusId) {
		this.cusId = cusId;
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

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}

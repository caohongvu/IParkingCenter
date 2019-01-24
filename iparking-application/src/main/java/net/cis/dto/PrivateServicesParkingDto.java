package net.cis.dto;

import java.util.Date;

public class PrivateServicesParkingDto {
	private long id;
	private Long parkingId;
	private String parkingCode;
	private Long privateServiceId;
	private String parkingServiceName;
	private Date createdAt;
	private Date updatedAt;
	private Integer status;

	public String getParkingCode() {
		return parkingCode;
	}

	public void setParkingCode(String parkingCode) {
		this.parkingCode = parkingCode;
	}

	public String getParkingServiceName() {
		return parkingServiceName;
	}

	public void setParkingServiceName(String parkingServiceName) {
		this.parkingServiceName = parkingServiceName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getParkingId() {
		return parkingId;
	}

	public void setParkingId(Long parkingId) {
		this.parkingId = parkingId;
	}

	public Long getPrivateServiceId() {
		return privateServiceId;
	}

	public void setPrivateServiceId(Long privateServiceId) {
		this.privateServiceId = privateServiceId;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}

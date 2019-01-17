package net.cis.dto;

import java.util.Date;

/**
 * Created by Vincent on 02/10/2018
 */
public class ParkingDto {

	 private Long id;
	 private String parkingCode;
	 private String address;
	 private String province;
	 private Long district;
	 private int status;
	 private Date iparkingJoined;
	 private int capacity;
	 private int adjust;
	 private int currentTicketInSession;
	 private String parkingPlaceData;
	 private Date createdAt;
	 private Date updatedAt;
	 private int company;
	 private boolean isProvideEInvoice;
	 private String lat;
	 private String lng;
	 private String oldId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getParkingCode() {
		return parkingCode;
	}
	public void setParkingCode(String parkingCode) {
		this.parkingCode = parkingCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public Long getDistrict() {
		return district;
	}
	public void setDistrict(Long district) {
		this.district = district;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getIparkingJoined() {
		return iparkingJoined;
	}
	public void setIparkingJoined(Date iparkingJoined) {
		this.iparkingJoined = iparkingJoined;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public int getAdjust() {
		return adjust;
	}
	public void setAdjust(int adjust) {
		this.adjust = adjust;
	}
	public int getRemain() {
		return (capacity + adjust)-currentTicketInSession;
	}
	
	public int getCurrentTicketInSession() {
		return currentTicketInSession;
	}
	public void setCurrentTicketInSession(int currentTicketInSession) {
		this.currentTicketInSession = currentTicketInSession;
	}
	public String getParkingPlaceData() {
		return parkingPlaceData;
	}
	public void setParkingPlaceData(String parkingPlaceData) {
		this.parkingPlaceData = parkingPlaceData;
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
	public int getCompany() {
		return company;
	}
	public void setCompany(int company) {
		this.company = company;
	}
	
	public boolean getIsProvideEInvoice() {
		return isProvideEInvoice;
	}
	public void setIsProvideEInvoice(boolean isProvideEInvoice) {
		this.isProvideEInvoice = isProvideEInvoice;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getOldId() {
		return oldId;
	}
	public void setOldId(String oldId) {
		this.oldId = oldId;
	}
	 
	 
	
}

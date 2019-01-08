package net.cis.dto;

import java.util.Date;

public class CustomerDto {
	private long id;
	private String phone;
	private String phone2;
	private String telco;
	private byte[] password;
	private int status;
	private Date createdAt;
	private Date updatedAt;
	private String checkSum;
	private long oldId;
	private String otp;

	public String getTelco() {
		return telco;
	}

	public void setTelco(String telco) {
		this.telco = telco;
	}

	public byte[] getPassword() {
		return password;
	}

	public void setPassword(byte[] password) {
		this.password = password;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getCheckSum() {
		return checkSum;
	}

	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
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

	public long getOldId() {
		return oldId;
	}

	public void setOldId(long oldId) {
		this.oldId = oldId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}

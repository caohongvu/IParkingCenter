package net.cis.dto;

import java.util.Date;

public class CustomerRecoveryDto {
	private long id;
	private long cusId;
	private Long checkSum;
	private Date expire;
	private Date createdAt;
	private Date updatedAt;

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCusId() {
		return cusId;
	}

	public void setCusId(long cusId) {
		this.cusId = cusId;
	}

	public Long getCheckSum() {
		return checkSum;
	}

	public void setCheckSum(Long checkSum) {
		this.checkSum = checkSum;
	}

	public Date getExpire() {
		return expire;
	}

	public void setExpire(Date expire) {
		this.expire = expire;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}

package net.cis.jpa.criteria;

import java.util.Date;

public class ParkingContractCriteria {
	private String cppCode;
	private Date fromDate;
	private Date toDate;
	private Integer status;

	public String getCppCode() {
		return cppCode;
	}

	public void setCppCode(String cppCode) {
		this.cppCode = cppCode;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}

package net.cis.jpa.criteria;

import java.util.Date;

/**
 * Created by Vincent on 02/10/2018
 */

public class MonthlyTicketCriteria {
	
	private Integer inSession;
	private String cppCode;
	private Date fromDate;
	private Date toDate;
	private Integer status;
	public Integer getInSession() {
		return inSession;
	}
	public void setInSession(Integer inSession) {
		this.inSession = inSession;
	}
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

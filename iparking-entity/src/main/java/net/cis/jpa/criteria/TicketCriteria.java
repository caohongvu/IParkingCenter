package net.cis.jpa.criteria;

import java.util.Date;

/**
 * Created by Vincent on 02/10/2018
 */

public class TicketCriteria {

	private Integer inSession;
	private Long cppId;
	private Date fromDate;
	private Date toDate;
	private Long customer;

	private Integer status;

	public Integer getInSession() {
		return inSession;
	}

	public void setInSession(Integer inSession) {
		this.inSession = inSession;
	}

	public Long getCppId() {
		return cppId;
	}

	public void setCppId(Long cppId) {
		this.cppId = cppId;
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

	public Long getCustomer() {
		return customer;
	}

	public void setCustomer(Long customer) {
		this.customer = customer;
	}

}

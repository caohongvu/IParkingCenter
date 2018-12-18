package net.cis.jpa.criteria;

/**
 * Created by Vincent on 02/10/2018
 */

public class TicketCriteria {
	
	private Integer inSession;
	private Long cppId;
	
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
	
	
}

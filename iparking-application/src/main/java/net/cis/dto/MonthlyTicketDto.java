package net.cis.dto;

import java.util.Date;

import net.cis.common.util.TicketUtil;
import net.cis.common.util.Utils;

/**
 * Created by Vincent on 02/10/2018
 */
public class MonthlyTicketDto {

	private long id;
	private String contractCode;
	private String contractNo;
	private String numberPlate;
	private String company;
	private String parkingPlace;
	private long validFrom;
	private long validTo;
	private int numberOfMonth;
	private long totalAmount;
	private long paidAmount;
	private Date createdAt;
	private Date updatedAt;
	private long validEnd;
	private String metadata;
	private long monthlyUnitPrice;
	private boolean inSession;
	private long inSessionTicketId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getNumberPlate() {
		return numberPlate;
	}
	public void setNumberPlate(String numberPlate) {
		this.numberPlate = numberPlate;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getParkingPlace() {
		return parkingPlace;
	}
	public void setParkingPlace(String parkingPlace) {
		this.parkingPlace = parkingPlace;
	}
	public long getValidFrom() {
		return validFrom;
	}
	public String getValidFromInFormat() {
		return Utils.getDatetimeFormatVN(validFrom);
	}
	public void setValidFrom(long validFrom) {
		this.validFrom = validFrom;
	}
	public long getValidTo() {
		return validTo;
	}
	public void setValidTo(long validTo) {
		this.validTo = validTo;
	}
	public String getValidToInFormat() {
		return Utils.getDatetimeFormatVN(validTo);
	}
	public int getNumberOfMonth() {
		return numberOfMonth;
	}
	public void setNumberOfMonth(int numberOfMonth) {
		this.numberOfMonth = numberOfMonth;
	}
	public long getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(long totalAmount) {
		this.totalAmount = totalAmount;
	}
	public long getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(long paidAmount) {
		this.paidAmount = paidAmount;
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
	public long getValidEnd() {
		return validEnd;
	}
	public void setValidEnd(long validEnd) {
		this.validEnd = validEnd;
	}
	public String getValidEndInFormat() {
		return Utils.getDatetimeFormatVN(validEnd);
	}
	public String getMetadata() {
		return metadata;
	}
	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}
	public long getMonthlyUnitPrice() {
		return monthlyUnitPrice;
	}
	public void setMonthlyUnitPrice(long monthlyUnitPrice) {
		this.monthlyUnitPrice = monthlyUnitPrice;
	}
	public boolean isInSession() {
		return inSession;
	}
	public void setInSession(boolean inSession) {
		this.inSession = inSession;
	}
	public long getInSessionTicketId() {
		return inSessionTicketId;
	}
	public void setInSessionTicketId(long inSessionTicketId) {
		this.inSessionTicketId = inSessionTicketId;
	}
	
	public String getSessionFrom() {
		return TicketUtil.getMonthlyTicketSessionFrom(this.metadata);
	}
	public String getSessionTo() {
		return TicketUtil.getMonthlyTicketSessionTo(this.metadata);
	}
	
	
	
}

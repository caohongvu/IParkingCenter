package net.cis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.cis.common.util.TicketUtil;

/**
 * Created by Vincent on 02/10/2018
 */
public class ParkingContractDto {

	private long id;
	private String contractCode;
	private String contractNo;
	private String company;
	private String parkingPlace;
	private String cppAddress;
	private long cusId;
	private long validFrom;
	private long validTo;
	private int numberOfMonth;
	private long totalAmount;
	private long paidAmount;
	private String invoiceConsolidated;
	private String createdAt;
	private String updatedAt;
	private long validEnd;
	private String metadata;
	private long monthlyUnitPrice;
	private boolean status;
	
	@JsonProperty("Invoice")
	private InvoiceCodeDto invoiceCodeDto;
	
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
	
	public String getCppAddress() {
		return cppAddress;
	}
	public void setCppAddress(String cppAddress) {
		this.cppAddress = cppAddress;
	}
	public long getCusId() {
		return cusId;
	}
	public void setCusId(long cusId) {
		this.cusId = cusId;
	}
	public long getValidFrom() {
		return validFrom;
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
	public String getInvoiceConsolidated() {
		return invoiceConsolidated;
	}
	public void setInvoiceConsolidated(String invoiceConsolidated) {
		this.invoiceConsolidated = invoiceConsolidated;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	public long getValidEnd() {
		return validEnd;
	}
	public void setValidEnd(long validEnd) {
		this.validEnd = validEnd;
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
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public String getSessionFrom() {
		return TicketUtil.getMonthlyTicketSessionFrom(this.metadata);
	}
	public String getSessionTo() {
		return TicketUtil.getMonthlyTicketSessionTo(this.metadata);
	}
	public InvoiceCodeDto getInvoiceCodeDto() {
		return invoiceCodeDto;
	}
	public void setInvoiceCodeDto(InvoiceCodeDto invoiceCodeDto) {
		this.invoiceCodeDto = invoiceCodeDto;
	}
	
	
	
}

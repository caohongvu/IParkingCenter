package net.cis.jpa.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ticket_transaction_portal_view")
public class TicketTransactionPortalEntity {
	@Id
    @Column(name="id")
//    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;
	
	@Column(name="ticket_id")
	private String ticketId;

	@Column(name="customer")
	private long customer;
	
	@Column(name="payment_method")
	private String paymentMethod;
	
	@Column(name="is_delegate")
	private long isDelegate;
	
	@Column(name="payment_order_no")
	private String paymentOrderNo;
	
	@Column(name="payment_order_type")
	private String paymentOrderType;
	
	@Column(name="transaction_type")
	private String transacionType;
	
	@Column(name="cus_token")
	private String cusToken;
	
	@Column(name="cus_token_scheme")
	private String cusTokenScheme;
	
	
	@Column(name="created_at")
	private Date createdAt;
	
	@Column(name="updated_at")
	private Date updatedAt;
	
	@Column(name="cash_holder")
	private String cashHolder;
	
	@Column(name="payment_amount")
	private Float paymentAmount;
	
	@Column(name="apply_from_time")
	private Date applyFromTime;
	
	@Column(name="apply_to_time")
	private Date applyToTime;
	
	@Column(name="payment_status")
	private int paymentStatus;

	@Column(name="auto_extend")
	private int autoExtend;
	

	
	@Column(name="parking_code")
	private String parkingCode;
	
	@Column(name="company_name")
	private String companyName;	
	
	@Column(name="invoice_code")
	private String invoiceCode;	
	
	@Column(name="card_number")
	private String cardNumber;	
	
	@Column(name="payment_trans_no")
	private String paymentTransNo;	

	
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getPaymentTransNo() {
		return paymentTransNo;
	}

	public void setPaymentTransNo(String paymentTransNo) {
		this.paymentTransNo = paymentTransNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public long getCustomer() {
		return customer;
	}

	public void setCustomer(long customer) {
		this.customer = customer;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public long getIsDelegate() {
		return isDelegate;
	}

	public void setIsDelegate(long isDelegate) {
		this.isDelegate = isDelegate;
	}

	public String getPaymentOrderNo() {
		return paymentOrderNo;
	}

	public void setPaymentOrderNo(String paymentOrderNo) {
		this.paymentOrderNo = paymentOrderNo;
	}

	public String getPaymentOrderType() {
		return paymentOrderType;
	}

	public void setPaymentOrderType(String paymentOrderType) {
		this.paymentOrderType = paymentOrderType;
	}

	public String getTransacionType() {
		return transacionType;
	}

	public void setTransacionType(String transacionType) {
		this.transacionType = transacionType;
	}

	public String getCusToken() {
		return cusToken;
	}

	public void setCusToken(String cusToken) {
		this.cusToken = cusToken;
	}

	public String getCusTokenScheme() {
		return cusTokenScheme;
	}

	public void setCusTokenScheme(String cusTokenScheme) {
		this.cusTokenScheme = cusTokenScheme;
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

	public String getCashHolder() {
		return cashHolder;
	}

	public void setCashHolder(String cashHolder) {
		this.cashHolder = cashHolder;
	}

	public Float getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Float paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Date getApplyFromTime() {
		return applyFromTime;
	}

	public void setApplyFromTime(Date applyFromTime) {
		this.applyFromTime = applyFromTime;
	}

	public Date getApplyToTime() {
		return applyToTime;
	}

	public void setApplyToTime(Date applyToTime) {
		this.applyToTime = applyToTime;
	}

	public int getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(int paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public int getAutoExtend() {
		return autoExtend;
	}

	public void setAutoExtend(int autoExtend) {
		this.autoExtend = autoExtend;
	}


	public String getParkingCode() {
		return parkingCode;
	}

	public void setParkingCode(String parkingCode) {
		this.parkingCode = parkingCode;
	}
	
}

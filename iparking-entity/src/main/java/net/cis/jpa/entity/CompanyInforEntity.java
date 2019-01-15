package net.cis.jpa.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "company_infor")
public class CompanyInforEntity {
	
	@JsonProperty("Id")
	@Id
    @Column(name="id")
	private int id;
	
	@JsonProperty("CompanyId")
	@Column(name="company_id")
	private int companyId;
	
	@JsonProperty("CompanyName")
	@Column(name="company_name")
	private String companyName;
	
	@JsonProperty("CompanyAddress")
	@Column(name="company_address")
	private String companyAddress;
	
	@JsonProperty("PhoneNumber")
	@Column(name="phone_number")
	private String phoneNumber;
	
	@JsonProperty("BankAccount")
	@Column(name="bank_account")
	private String bankAccount;
	
	@JsonProperty("RegistrationCertificate")
	@Column(name="registration_certificate")
	private String registrationCertificate;
	
	@JsonProperty("TaxCode")
	@Column(name="tax_code")
	private String taxCode;
	
	@JsonProperty("DateActive")
	@Column(name="date_active")
	private Date dateActive;
	
	@JsonProperty("InforContact")
	@Column(name="infor_contact")
	private String inforContact;
	
	@JsonProperty("DailyInvoice")
	@Column(name="daily_invoice")
	private int dailyInvoice;
	
	@JsonProperty("MonthlyInvoice")
	@Column(name="monthly_invoice")
	private int monthlyInvoice;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getRegistrationCertificate() {
		return registrationCertificate;
	}

	public void setRegistrationCertificate(String registrationCertificate) {
		this.registrationCertificate = registrationCertificate;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public Date getDateActive() {
		return dateActive;
	}

	public void setDateActive(Date dateActive) {
		this.dateActive = dateActive;
	}

	public String getInforContact() {
		return inforContact;
	}

	public void setInforContact(String inforContact) {
		this.inforContact = inforContact;
	}

	public int getDailyInvoice() {
		return dailyInvoice;
	}

	public void setDailyInvoice(int dailyInvoice) {
		this.dailyInvoice = dailyInvoice;
	}

	public int getMonthlyInvoice() {
		return monthlyInvoice;
	}

	public void setMonthlyInvoice(int monthlyInvoice) {
		this.monthlyInvoice = monthlyInvoice;
	}
	
	
	 
}

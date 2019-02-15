package net.cis.jpa.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bill")
public class BillEntity {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "parking_place")
	private Long parkingPlace;

	@Column(name = "customer")
	private Long customer;

	@Column(name = "info")
	private String info;

	@Column(name = "must_pay_amount")
	private Double mustPayAmount;

	@Column(name = "status")
	private Integer status;

	@ManyToOne
	@JoinColumn(name = "private_services_parking_customer_id", referencedColumnName = "id")
	private PrivateServicesParkingCusEntity privateServicesParkingCus;

	@Column(name = "description")
	private String description;

	@Column(name = "sysn_date")
	private Date sysnDate;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getParkingPlace() {
		return parkingPlace;
	}

	public void setParkingPlace(Long parkingPlace) {
		this.parkingPlace = parkingPlace;
	}

	public Long getCustomer() {
		return customer;
	}

	public void setCustomer(Long customer) {
		this.customer = customer;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Double getMustPayAmount() {
		return mustPayAmount;
	}

	public void setMustPayAmount(Double mustPayAmount) {
		this.mustPayAmount = mustPayAmount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public PrivateServicesParkingCusEntity getPrivateServicesParkingCus() {
		return privateServicesParkingCus;
	}

	public void setPrivateServicesParkingCus(PrivateServicesParkingCusEntity privateServicesParkingCus) {
		this.privateServicesParkingCus = privateServicesParkingCus;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getSysnDate() {
		return sysnDate;
	}

	public void setSysnDate(Date sysnDate) {
		this.sysnDate = sysnDate;
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

}

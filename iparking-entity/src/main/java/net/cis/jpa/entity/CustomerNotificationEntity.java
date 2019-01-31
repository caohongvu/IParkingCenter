package net.cis.jpa.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer_notification")
public class CustomerNotificationEntity {
	@Id
	@Column(name = "id")
	private long id;
	@Column(name = "cus_id")
	private long cusId;

	@Column(name = "device_id")
	private String deviceId;

	@Column(name = "hash_device_id")
	private long hashDeviceId;

	@Column(name = "device_name")
	private String deviceName;

	@Column(name = "os")
	private String os;

	@Column(name = "token")
	private String token;

	@Column(name = "version_code")
	private Integer versionCode;

	@Column(name = "version_name")
	private String versionName;

	@Column(name = "package_name")
	private String packageName;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "updated_by")
	private long updatedBy;

	@Column(name = "subscrice")
	private Integer subscrice;

	public Integer getSubscrice() {
		return subscrice;
	}

	public void setSubscrice(Integer subscrice) {
		this.subscrice = subscrice;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public long getHashDeviceId() {
		return hashDeviceId;
	}

	public void setHashDeviceId(long hashDeviceId) {
		this.hashDeviceId = hashDeviceId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(Integer versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(long updatedBy) {
		this.updatedBy = updatedBy;
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}

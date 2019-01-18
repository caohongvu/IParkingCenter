package net.cis.dto;

import java.util.Date;

public class ParkingConfigDto {
	private long id;

	private String configKey;

	private String configValue;

	private String configDescription;

	private String parkingCode;

	private long parkingId;

	private String company;

	private long companyId;

	private Date createdAt;

	private Date updatedAt;

	private Date lastDateRun;

	private long parkingConfigTypeId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getConfigKey() {
		return configKey;
	}

	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	public String getConfigDescription() {
		return configDescription;
	}

	public void setConfigDescription(String configDescription) {
		this.configDescription = configDescription;
	}

	public String getParkingCode() {
		return parkingCode;
	}

	public void setParkingCode(String parkingCode) {
		this.parkingCode = parkingCode;
	}

	public long getParkingId() {
		return parkingId;
	}

	public void setParkingId(long parkingId) {
		this.parkingId = parkingId;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
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

	public Date getLastDateRun() {
		return lastDateRun;
	}

	public void setLastDateRun(Date lastDateRun) {
		this.lastDateRun = lastDateRun;
	}

	public long getParkingConfigTypeId() {
		return parkingConfigTypeId;
	}

	public void setParkingConfigTypeId(long parkingConfigTypeId) {
		this.parkingConfigTypeId = parkingConfigTypeId;
	}

}

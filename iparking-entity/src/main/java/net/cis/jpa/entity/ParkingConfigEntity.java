package net.cis.jpa.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "parking_config")
public class ParkingConfigEntity {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "config_key")
	private String configKey;

	@Column(name = "config_value")
	private String configValue;

	@Column(name = "config_description")
	private String configDescription;

	@Column(name = "company_id")
	private Long companyId;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "last_date_run")
	private Date lastDateRun;

	@Column(name = "parking_config_type_id")
	private long parkingConfigTypeId;

	public long getParkingConfigTypeId() {
		return parkingConfigTypeId;
	}

	public void setParkingConfigTypeId(long parkingConfigTypeId) {
		this.parkingConfigTypeId = parkingConfigTypeId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
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

	public Date getLastDateRun() {
		return lastDateRun;
	}

	public void setLastDateRun(Date lastDateRun) {
		this.lastDateRun = lastDateRun;
	}

}

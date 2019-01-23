package net.cis.jpa.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "parking_place_history")
public class ParkingPlaceHistoryEntity {
	@Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="old_id")
	private String oldId;
	
	@Column(name="updated_at")
	private String updatedAt;
	
	@Column(name="user_name")
	private String userName;
	
	@Column(name="info_update")
	private String infoUpdate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOldId() {
		return oldId;
	}

	public void setOldId(String oldId) {
		this.oldId = oldId;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getInfoUpdate() {
		return infoUpdate;
	}

	public void setInfoUpdate(String infoUpdate) {
		this.infoUpdate = infoUpdate;
	}
	
	


}

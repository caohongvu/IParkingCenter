package net.cis.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class UserEntity {
	
	@Id
    @Column(name="id")
//    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="username")
	private String username;
	
	@Column(name="fullname")
	private String fullname;
	
	@Column(name="title")
	private String title;
	
	@Column(name="status")
	private int status;
	
	@Column(name="created_at")
	private String created_at;
	
	@Column(name="updated_at")
	private String updated_at;
	
	@Column(name="checksum")
	private String checksum;
	
	@Column(name="pwd_counter")
	private int pwd_counter;
	
	@Column(name="type")
	private int type;
	
	@OneToOne(mappedBy="userEntity")
	private UserMetadataEntity userMetadata;
	
	@OneToOne
	@JoinColumn(name="username", referencedColumnName="username", insertable = false, updatable = false, nullable = false)
	private UserSecurityEntity userSecurityEntity;
	
	



	public UserSecurityEntity getUserSecurityEntity() {
		return userSecurityEntity;
	}

	public void setUserSecurityEntity(UserSecurityEntity userSecurityEntity) {
		this.userSecurityEntity = userSecurityEntity;
	}

	public UserMetadataEntity getUserMetadata() {
		return userMetadata;
	}

	public void setUserMetadata(UserMetadataEntity userMetadata) {
		this.userMetadata = userMetadata;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public int getPwd_counter() {
		return pwd_counter;
	}

	public void setPwd_counter(int pwd_counter) {
		this.pwd_counter = pwd_counter;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	

}

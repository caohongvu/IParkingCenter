package net.cis.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="user_security")
public class UserSecurityEntity {
	
	@Id
	@Column(name="username")
	private String username;
	
	@Column(name="gr")
	private int gr;
	
	@Column(name="role")
	private int role;
	
	@Column(name="password")
	private String password;
	
	@Column(name="recovery")
	private String recovery;
	

	@Column(name="created_at")
	private String created_at;
	
	@Column(name="updated_at")
	private String updated_at;
	
	@Column(name="checksum")
	private String checksum;
	
	@Column(name="roleDelegatePayment")
	private String roleDelegatePayment;
	
	
	@OneToOne(mappedBy="userSecurityEntity")
	@JoinColumn(name = "username", insertable = true, updatable = false, nullable = false)
	private UserEntity userEntity;
	
	

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getGr() {
		return gr;
	}

	public void setGr(int gr) {
		this.gr = gr;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRecovery() {
		return recovery;
	}

	public void setRecovery(String recovery) {
		this.recovery = recovery;
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

	public String getRoleDelegatePayment() {
		return roleDelegatePayment;
	}

	public void setRoleDelegatePayment(String roleDelegatePayment) {
		this.roleDelegatePayment = roleDelegatePayment;
	}
	
}

package net.cis.dto;

public class UserSecurityDto {
	private String username;
	private int gr;
	private int role;
	private String password;
	private String recovery;
	private String created_at;
	private String updated_at;
	private long checksum;
	private int roleDelegatePayment;
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
	public long getChecksum() {
		return checksum;
	}
	public void setChecksum(long checksum) {
		this.checksum = checksum;
	}
	public int getRoleDelegatePayment() {
		return roleDelegatePayment;
	}
	public void setRoleDelegatePayment(int roleDelegatePayment) {
		this.roleDelegatePayment = roleDelegatePayment;
	}

}

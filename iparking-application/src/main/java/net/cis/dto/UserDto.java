package net.cis.dto;

public class UserDto {
	private int id;
	private String username;
	private String title;
	private String fullname;
	private int status;
	private String created_at;
	private String updated_at;
	private long checksum;
	private int pwd_counter;
	private int type;
	private String password;
	
	private String email;
	private String phone;
	private String recovery;
		
	
	
	
	
	
	
	public String getRecovery() {
		return recovery;
	}
	public void setRecovery(String recovery) {
		this.recovery = recovery;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
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

	public long getChecksum() {
		return checksum;
	}
	public void setChecksum(long checksum) {
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

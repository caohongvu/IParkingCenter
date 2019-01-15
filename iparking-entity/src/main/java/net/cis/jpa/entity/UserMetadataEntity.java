package net.cis.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name ="user_metadata")
public class UserMetadataEntity {
	@Id
    @Column(name="id")
	private int id;
	
	@Column(name ="email")
	private String email;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="telco")
	private String telco;
	
	@Column(name="gender")
	private int gender;
	
	@Column(name="birthday")
	private String birthday;
	
	@Column(name="address")
	private String address;
	
	@Column(name="avatar")
	private String avatar;
	
	@Column(name="national_id")
	private String national_id;
	
	@Column(name="passport_date")
	private String passport_date;
	
	@Column(name="home_id")
	private String home_id;
	
	@Column(name="data")
	private String data;
	
	@Column(name="email_verified")
	private int email_verifed;
	
	@Column(name="email_verification_code")
	private String email_veryfication_code;
	
	@Column(name="updated_at")
	private String updated_at;
	
	
	
	@OneToOne()
	@JoinColumn(name = "id", insertable = true, updatable = false, nullable = false)
	private UserEntity userEntity;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTelco() {
		return telco;
	}

	public void setTelco(String telco) {
		this.telco = telco;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getNational_id() {
		return national_id;
	}

	public void setNational_id(String national_id) {
		this.national_id = national_id;
	}

	public String getPassport_date() {
		return passport_date;
	}

	public void setPassport_date(String passport_date) {
		this.passport_date = passport_date;
	}

	public String getHome_id() {
		return home_id;
	}

	public void setHome_id(String home_id) {
		this.home_id = home_id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getEmail_verifed() {
		return email_verifed;
	}

	public void setEmail_verifed(int email_verifed) {
		this.email_verifed = email_verifed;
	}


	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public String getEmail_veryfication_code() {
		return email_veryfication_code;
	}

	public void setEmail_veryfication_code(String email_veryfication_code) {
		this.email_veryfication_code = email_veryfication_code;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
	
	
	

}

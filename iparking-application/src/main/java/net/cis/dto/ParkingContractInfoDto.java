package net.cis.dto;

public class ParkingContractInfoDto {
	private long id;

	private String contractCode;

	private String contractNo;

	private String company;

	private String parkingPlace;

	private long cusId;

	private String phone2;

	private String fullName;

	private String email;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getParkingPlace() {
		return parkingPlace;
	}

	public void setParkingPlace(String parkingPlace) {
		this.parkingPlace = parkingPlace;
	}

	public long getCusId() {
		return cusId;
	}

	public void setCusId(long cusId) {
		this.cusId = cusId;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}

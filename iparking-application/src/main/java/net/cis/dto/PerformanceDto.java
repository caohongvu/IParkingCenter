package net.cis.dto;

public class PerformanceDto {
	private String cppCode;
	private double percent;
	private int cppId;
	private String address;
	private int capacity;

	public String getCppCode() {
		return cppCode;
	}

	public void setCppCode(String cppCode) {
		this.cppCode = cppCode;
	}

	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}

	public int getCppId() {
		return cppId;
	}

	public void setCppId(int cppId) {
		this.cppId = cppId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

}

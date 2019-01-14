package net.cis.dto;

public class PerformanceExtractionDto {
	private long cppId;
	private String parkingCode;
	private String address;
	private double performance;

	public long getCppId() {
		return cppId;
	}

	public void setCppId(long cppId) {
		this.cppId = cppId;
	}

	public String getParkingCode() {
		return parkingCode;
	}

	public void setParkingCode(String parkingCode) {
		this.parkingCode = parkingCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getPerformance() {
		return performance;
	}

	public void setPerformance(double performance) {
		this.performance = performance;
	}

}

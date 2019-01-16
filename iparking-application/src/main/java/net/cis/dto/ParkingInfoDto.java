package net.cis.dto;

public class ParkingInfoDto {
	private long id;
	private long cppId;
	private String paymentRule;
	private Integer timeAvg;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCppId() {
		return cppId;
	}

	public void setCppId(long cppId) {
		this.cppId = cppId;
	}

	public String getPaymentRule() {
		return paymentRule;
	}

	public void setPaymentRule(String paymentRule) {
		this.paymentRule = paymentRule;
	}

	public Integer getTimeAvg() {
		return timeAvg;
	}

	public void setTimeAvg(Integer timeAvg) {
		this.timeAvg = timeAvg;
	}

}

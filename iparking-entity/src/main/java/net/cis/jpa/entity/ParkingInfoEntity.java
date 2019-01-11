package net.cis.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "parking_place_info")
public class ParkingInfoEntity {
	@Id
	@Column(name = "id")
	private long id;

	@Column(name = "carpp_id")
	private long carppId;

	@Column(name = "payment_rule")
	private String paymentRule;

	@Column(name = "time_avg")
	private int timeAvg;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCarppId() {
		return carppId;
	}

	public void setCarppId(long carppId) {
		this.carppId = carppId;
	}

	public String getPaymentRule() {
		return paymentRule;
	}

	public void setPaymentRule(String paymentRule) {
		this.paymentRule = paymentRule;
	}

	public int getTimeAvg() {
		return timeAvg;
	}

	public void setTimeAvg(int timeAvg) {
		this.timeAvg = timeAvg;
	}

}

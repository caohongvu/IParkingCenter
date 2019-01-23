package net.cis.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Vincent on 02/10/2018
 */
@Entity
@Table(name = "parking_place_actor")
public class ParkingActorEntity {

	@Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="actor")
	private long actor;
	
	@Column(name="cpp_id")
	private long cppId;
	
	@Column(name="balance_account_id")
	private long balanceAccountId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getActor() {
		return actor;
	}

	public void setActor(long actor) {
		this.actor = actor;
	}

	public long getCppId() {
		return cppId;
	}

	public void setCppId(long cppId) {
		this.cppId = cppId;
	}

	public long getBalanceAccountId() {
		return balanceAccountId;
	}

	public void setBalanceAccountId(long balanceAccountId) {
		this.balanceAccountId = balanceAccountId;
	}
	
	
	
}

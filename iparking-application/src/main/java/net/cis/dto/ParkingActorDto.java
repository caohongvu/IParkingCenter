package net.cis.dto;

/**
 * Created by Vincent on 02/10/2018
 */
public class ParkingActorDto {
	private long id;

	private long actor;

	private long cppId;
	
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

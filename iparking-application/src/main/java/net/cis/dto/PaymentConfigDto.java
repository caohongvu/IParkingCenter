package net.cis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentConfigDto {
	
	@JsonProperty("ItemName")
	private String itemName;
	
	@JsonProperty("Price")
	private long price;

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}
	
}

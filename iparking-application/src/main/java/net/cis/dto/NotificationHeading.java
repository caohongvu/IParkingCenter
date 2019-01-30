package net.cis.dto;

import com.google.gson.annotations.SerializedName;

public class NotificationHeading {
	@SerializedName("en")
	private String en;

	@SerializedName("vi")
	private String vi;

	public String getEn() {
		return en;
	}

	public void setEn(String en) {
		this.en = en;
	}

	public String getVi() {
		return vi;
	}

	public void setVi(String vi) {
		this.vi = vi;
	}
}

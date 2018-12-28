package net.cis.dto;

import com.google.gson.annotations.SerializedName;

public class NotificationData {
	@SerializedName("detail")
	private String detail;

	@SerializedName("title")
	private String title;

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}

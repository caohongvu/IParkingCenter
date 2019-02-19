package net.cis.dto;

import com.google.gson.annotations.SerializedName;

import net.cis.constants.NotificationTypeEnum;

public class NotificationData {
	@SerializedName("type_content")
	private NotificationTypeEnum type;

	@SerializedName("id")
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public NotificationTypeEnum getType() {
		return type;
	}

	public void setType(NotificationTypeEnum type) {
		this.type = type;
	}

}

package net.cis.dto;

import com.google.gson.annotations.SerializedName;

import net.cis.constants.NotificationTypeEnum;

public class NotificationData {
	@SerializedName("type_content")
	private NotificationTypeEnum type;

	public NotificationTypeEnum getType() {
		return type;
	}

	public void setType(NotificationTypeEnum type) {
		this.type = type;
	}

}

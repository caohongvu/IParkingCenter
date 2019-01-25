package net.cis.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class NotificationCustomerDto {
	private long id;
	private Long notificationId;
	private Long cusId;
	private Date createdAt;
	@JsonInclude(Include.NON_NULL)
	private NotificationDto notification;

	public NotificationDto getNotification() {
		return notification;
	}

	public void setNotification(NotificationDto notification) {
		this.notification = notification;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Long notificationId) {
		this.notificationId = notificationId;
	}

	public Long getCusId() {
		return cusId;
	}

	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}

}
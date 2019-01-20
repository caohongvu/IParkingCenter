package net.cis.dto;

import java.util.Date;
import java.util.List;

public class NotificationDto {
	private long id;
	private String title;
	private String content;
	private Date createdAt;
	private Long createdBy;
	private String createdByUserName;
	private String createdByFullName;
	private List<Integer> types;

	public List<Integer> getTypes() {
		return types;
	}

	public void setTypes(List<Integer> types) {
		this.types = types;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCreatedByFullName() {
		return createdByFullName;
	}

	public void setCreatedByFullName(String createdByFullName) {
		this.createdByFullName = createdByFullName;
	}

	public String getCreatedByUserName() {
		return createdByUserName;
	}

	public void setCreatedByUserName(String createdByUserName) {
		this.createdByUserName = createdByUserName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

}

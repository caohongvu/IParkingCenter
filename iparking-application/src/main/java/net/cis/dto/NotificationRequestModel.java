package net.cis.dto;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class NotificationRequestModel {
	@SerializedName("data")
	private NotificationData data;
	@SerializedName("contents")
	private NotificationContent contents;
	@SerializedName("app_id")
	private String appId;
	@SerializedName("include_player_ids")
	private List<String> playerIds;

	@SerializedName("included_segments")
	private String includedSegments;

	public String getIncludedSegments() {
		return includedSegments;
	}

	public void setIncludedSegments(String includedSegments) {
		this.includedSegments = includedSegments;
	}

	public NotificationData getData() {
		return data;
	}

	public void setData(NotificationData data) {
		this.data = data;
	}

	public NotificationContent getContents() {
		return contents;
	}

	public void setContents(NotificationContent contents) {
		this.contents = contents;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public List<String> getPlayerIds() {
		return playerIds;
	}

	public void setPlayerIds(List<String> playerIds) {
		this.playerIds = playerIds;
	}

}

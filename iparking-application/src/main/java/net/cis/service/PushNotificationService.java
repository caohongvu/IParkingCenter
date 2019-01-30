package net.cis.service;

import java.util.List;

import net.cis.constants.NotificationTypeEnum;

public interface PushNotificationService {

	void sendNotificationForPlayerIds(List<String> playerIds, NotificationTypeEnum enumType, String message)
			throws Exception;

	void sendNotificationForPlayerId(String playerId, NotificationTypeEnum enumType, String message) throws Exception;

	void sendNotificationForSpecificSegment(String segment, NotificationTypeEnum enumType, String message)
			throws Exception;

	void sendNotificationForAllSubscribers(NotificationTypeEnum enumType, String message) throws Exception;

}

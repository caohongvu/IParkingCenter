package net.cis.service;

import java.util.List;

public interface PushNotificationService {

	void sendNotificationForPlayerIds(List<String> playerIds, String message) throws Exception;

	void sendNotificationForPlayerId(String playerId, String message) throws Exception;

	void sendNotificationForSpecificSegment(String segment, String message) throws Exception;

	void sendNotificationForAllSubscribers(String message) throws Exception;

}

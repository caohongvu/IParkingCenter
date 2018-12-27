package net.cis.service;

import java.util.List;

public interface PushNotificationService {
	void sendPushNotification(List<String> deviceTokenList);

	void sendRemoteMessage(String fcmToken, String id, String message) throws Exception;

	void sendInAppMessage(String fcmToken, String id, String message, long secure) throws Exception;

	void sendAppBroadcastNotitication(String id, String message, String appCode, long secure) throws Exception;

}

package net.cis.service;

public interface PushNotificationService {

	void sendInAppMessage(String fcmToken, String title, String message) throws Exception;

}

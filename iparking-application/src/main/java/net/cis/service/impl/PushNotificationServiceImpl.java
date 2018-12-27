package net.cis.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import net.cis.service.PushNotificationService;
import net.cis.utils.ParkingCenterConstants;

@Service
public class PushNotificationServiceImpl implements PushNotificationService {
	protected final Logger LOGGER = Logger.getLogger(getClass());

	@Override
	public void sendPushNotification(List<String> deviceTokenList) {
		// TODO Auto-generated method stub
	}

	public void sendRemoteMessage(String fcmToken, String id, String message) throws IOException {
		sendMessage(fcmToken, message, id, ParkingCenterConstants.REMOTE_NOTIFICATION);
	}

	public void sendInAppMessage(String fcmToken, String id, String message, long secure) throws IOException {
		String type = secure == 1 ? ParkingCenterConstants.SECURE_IN_APP_NOTIFICATION
				: ParkingCenterConstants.INSECURE_IN_APP_NOTIFICATION;
		sendMessage(fcmToken, id, message, type);
	}

	public void sendAppBroadcastNotitication(String id, String message, String appCode, long secure)
			throws IOException {
		String to = "/topics/" + appCode;
		String type = secure == 1 ? ParkingCenterConstants.SECURE_IN_APP_NOTIFICATION
				: ParkingCenterConstants.INSECURE_IN_APP_NOTIFICATION;
		sendMessage(to, id, message, type);
	}

	private void sendMessage(String to, String id, String message, String type) throws IOException {
		URL url = new URL(ParkingCenterConstants.API_URL_FCM);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", "key=" + ParkingCenterConstants.AUTH_KEY_FCM);
		conn.setRequestProperty("Content-Type", "application/json");
		JSONObject json = new JSONObject();
		try {
			json.put("to", to);
			JSONObject data = new JSONObject();
			UUID uuid = UUID.randomUUID();
			data.put("id", uuid);
			data.put("icon", "");
			data.put("type", type);
			data.put("title", message);
			data.put("message", message);
			data.put("messageId", id);
			json.put("data", data);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
			bw.write(json.toString());
			bw.flush();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("FCM Notification is sent successfully");
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		PushNotificationServiceImpl test = new PushNotificationServiceImpl();
		String device = "e9af6e33-1c59-4bf0-abc9-ba6d03610ab7;co9Xf7gws7s:APA91bHQijGUrKwMZNj0C1CtD8Whp964p_7ubRxWn2plzkJxlHE7kF72GBXL-0mbmHk4gVZ4Wx1IRKBOWS4Z264BTgk8qOcpGk3v9_gkEJ_pSXQEuwAeFuxDK_jhCVwBkUt3vGVdJkbU";
		test.sendMessage(device, "12123", "Test", "REMOTE_NOTIFICATION");
	}

}

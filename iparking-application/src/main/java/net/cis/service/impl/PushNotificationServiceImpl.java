package net.cis.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.cis.dto.NotificationData;
import net.cis.dto.NotificationRequestModel;
import net.cis.service.PushNotificationService;
import net.cis.utils.ParkingCenterConstants;

@Service
public class PushNotificationServiceImpl implements PushNotificationService {
	protected final Logger LOGGER = Logger.getLogger(getClass());

	public void sendInAppMessage(String fcmToken, String title, String message) throws IOException {
		sendMessage(fcmToken, title, message);
	}

	private void sendMessage(String fcmToken, String title, String message) throws IOException {
		URL url = new URL(ParkingCenterConstants.API_URL_FCM);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", "key=" + ParkingCenterConstants.AUTH_KEY_FCM);
		conn.setRequestProperty("Content-Type", "application/json");
		try {
			NotificationRequestModel notificationRequestModel = new NotificationRequestModel();
			NotificationData notificationData = new NotificationData();
			notificationData.setDetail(message);
			notificationData.setTitle(title);
			notificationRequestModel.setData(notificationData);
			notificationRequestModel.setTo(fcmToken);
			Gson gson = new Gson();
			Type type = new TypeToken<NotificationRequestModel>() {
			}.getType();
			String json = gson.toJson(notificationRequestModel, type);
			LOGGER.info("Request:" + json);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
			bw.write(json.toString());
			bw.flush();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;
			while ((output = br.readLine()) != null) {
				LOGGER.info("Response:" + output);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		LOGGER.info("FCM Notification is sent successfully");
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		PushNotificationServiceImpl test = new PushNotificationServiceImpl();
		String device = "e9af6e33-1c59-4bf0-abc9-ba6d03610ab7;co9Xf7gws7s:APA91bHQijGUrKwMZNj0C1CtD8Whp964p_7ubRxWn2plzkJxlHE7kF72GBXL-0mbmHk4gVZ4Wx1IRKBOWS4Z264BTgk8qOcpGk3v9_gkEJ_pSXQEuwAeFuxDK_jhCVwBkUt3vGVdJkbU";
		test.sendMessage(device, "12123", "Test");
	}

}

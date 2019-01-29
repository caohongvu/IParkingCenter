package net.cis.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.cis.dto.NotificationContent;
import net.cis.dto.NotificationData;
import net.cis.dto.NotificationRequestModel;
import net.cis.service.PushNotificationService;
import net.cis.utils.ParkingCenterConstants;

@Service
public class PushNotificationServiceImpl implements PushNotificationService {
	protected final Logger LOGGER = Logger.getLogger(getClass());

	public void sendNotificationForPlayerIds(List<String> playerIds, String message) throws IOException {
		URL url = new URL(ParkingCenterConstants.API_URL_FCM);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", ParkingCenterConstants.AUTH_KEY_FCM);
		conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		try {
			NotificationRequestModel notificationRequestModel = new NotificationRequestModel();
			NotificationData notificationData = new NotificationData();
			notificationData.setDetail(message);
			NotificationContent content = new NotificationContent();
			content.setEn(message);
			notificationRequestModel.setAppId(ParkingCenterConstants.APP_ID);
			notificationRequestModel.setData(notificationData);
			notificationRequestModel.setPlayerIds(playerIds);
			notificationRequestModel.setContents(content);
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

	@Override
	public void sendNotificationForSpecificSegment(String segment, String message) throws Exception {
		URL url = new URL(ParkingCenterConstants.API_URL_FCM);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", ParkingCenterConstants.AUTH_KEY_FCM);
		conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		try {
			NotificationRequestModel notificationRequestModel = new NotificationRequestModel();
			NotificationData notificationData = new NotificationData();
			notificationData.setDetail(message);
			NotificationContent content = new NotificationContent();
			content.setEn(message);
			notificationRequestModel.setAppId(ParkingCenterConstants.APP_ID);
			notificationRequestModel.setData(notificationData);
			notificationRequestModel.setIncludedSegments(segment);
			notificationRequestModel.setContents(content);
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

	@Override
	public void sendNotificationForAllSubscribers(String message) throws Exception {
		URL url = new URL(ParkingCenterConstants.API_URL_FCM);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", ParkingCenterConstants.AUTH_KEY_FCM);
		conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		try {
			NotificationRequestModel notificationRequestModel = new NotificationRequestModel();
			NotificationData notificationData = new NotificationData();
			notificationData.setDetail(message);
			NotificationContent content = new NotificationContent();
			content.setEn(message);
			notificationRequestModel.setAppId(ParkingCenterConstants.APP_ID);
			notificationRequestModel.setData(notificationData);
			notificationRequestModel.setIncludedSegments("All");
			notificationRequestModel.setContents(content);
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
		PushNotificationServiceImpl test = new PushNotificationServiceImpl();
		List<String> playerIds = new ArrayList<String>();
		playerIds.add("e9af6e33-1c59-4bf0-abc9-ba6d03610ab7");
		test.sendNotificationForPlayerIds(playerIds, "Test");
	}

}

package net.cis.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import net.cis.common.util.constant.URLConstants;
import net.cis.service.SmsService;
import net.cis.utils.RestfulUtil;

@Service
public class SmsServiceImpl implements SmsService {

	protected final Logger LOGGER = Logger.getLogger(getClass());

	@Override
	public boolean sendSms(String phone, String content) {
		// TODO Auto-generated method stub
		String finalURL = URLConstants.URL_SEND_OTP;
		List<NameValuePair> formParams = new ArrayList<>();
		formParams.add(new BasicNameValuePair("phone", phone));
		formParams.add(new BasicNameValuePair("content", content));
		String responseContent = RestfulUtil.postFormData(finalURL, formParams, MediaType.APPLICATION_JSON_VALUE);
		LOGGER.info("Result sendSms:" + responseContent);
		return parseJSonToSmsResponseObject(responseContent);
	}

	private boolean parseJSonToSmsResponseObject(String dataResult) {
		try {
			JSONObject ticketJSon = new JSONObject(dataResult);
			JSONObject ticketDataJSon = ticketJSon.getJSONObject("Error");
			if (ticketDataJSon.has("Code") && HttpStatus.OK.toString().equals(ticketDataJSon.getString("Code"))) {
				return Boolean.TRUE;
			}
			return Boolean.FALSE;
		} catch (Exception ex) {
			LOGGER.error("Loi gui sms send otp:" + ex.getMessage());
			return Boolean.FALSE;
		}
	}

}

package net.cis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import net.cis.common.util.constant.URLConstants;
import net.cis.service.CustomerService;
import net.cis.utils.RestfulUtil;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Override
	public Map<String, Object> createCustomerInPoseidonDb(String phone) throws Exception {
		// TODO Auto-generated method stub
		String finalURL = URLConstants.URL_CREATE_CUSTOMER;
		List<NameValuePair> formParams = new ArrayList<>();
		formParams.add(new BasicNameValuePair("phone", phone));
		String responseContent = RestfulUtil.postFormData(finalURL, formParams,
				MediaType.APPLICATION_FORM_URLENCODED_VALUE);
		return parseJSonToCreatePoseidonResponseObject(responseContent);
	}

	private Map<String, Object> parseJSonToCreatePoseidonResponseObject(String dataResult) throws JSONException {
		Map<String, Object> result = new HashMap<String, Object>();
		JSONObject ticketJSon = new JSONObject(dataResult);
		JSONObject ticketDataJSon = ticketJSon.getJSONObject("Error");
		if (ticketDataJSon.has("Code")) {
			result.put("Code", ticketDataJSon.getString("Code"));
		}
		if (ticketDataJSon.has("Message")) {
			result.put("Message", ticketDataJSon.getString("Message"));
		}
		if (ticketJSon.has("Data")) {
			result.put("Data", ticketJSon.getString("Data"));
		}
		return result;
	}
}

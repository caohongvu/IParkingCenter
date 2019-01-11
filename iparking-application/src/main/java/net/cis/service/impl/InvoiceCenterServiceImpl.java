package net.cis.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import net.cis.service.InvoiceCenterService;
import net.cis.utils.InvoiceCenterConstants;
import net.cis.utils.RestfulUtil;

/**
 * Created by NhanNguyen
 */
@Service
public class InvoiceCenterServiceImpl implements InvoiceCenterService {

	@Override
	public List<String> getInvoiceCode(Long ticketId) throws Exception {
		List<String> codes = new ArrayList<String>();
		
		String response = RestfulUtil.get(InvoiceCenterConstants.GET_INVOICE_CODE_FOR_MONTHLY + ticketId, "application/json");
		JSONObject json = new JSONObject(response);
		JSONArray data = json.getJSONArray("Data");
		
		for (int i = 0; i < data.length(); i++) {
			codes.add(data.getString(i));
		}
		
		return codes;
	}

	
	
}

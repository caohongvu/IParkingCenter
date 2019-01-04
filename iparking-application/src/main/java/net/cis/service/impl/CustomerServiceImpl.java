package net.cis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import net.cis.common.util.constant.URLConstants;
import net.cis.dto.CustomerCarDto;
import net.cis.dto.CustomerDto;
import net.cis.dto.CustomerInfoDto;
import net.cis.jpa.entity.CustomerCarEntity;
import net.cis.jpa.entity.CustomerEntity;
import net.cis.jpa.entity.CustomerInfoEntity;
import net.cis.jpa.entity.TicketEntity;
import net.cis.repository.CustomerCarRepository;
import net.cis.repository.CustomerInfoRepository;
import net.cis.repository.CustomerRepository;
import net.cis.service.CustomerService;
import net.cis.utils.RestfulUtil;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	CustomerCarRepository customerCarRepository;

	@Autowired
	CustomerInfoRepository customerInfoRepository;

	ModelMapper mapper;

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

	@Override
	public CustomerEntity createCustomerInIparkingCenter(CustomerEntity customerEntity) {
		// TODO Auto-generated method stub
		return customerRepository.save(customerEntity);
	}

	@Override
	public List<CustomerCarDto> findCustomerCarByNumberPlate(String numberPlate, int verified) throws Exception {
		List<CustomerCarEntity> lstCustomerCarEntity = customerCarRepository.findCustomerCarByNumberPlate(numberPlate,
				verified);
		return this.map(lstCustomerCarEntity);
	}

	private List<CustomerCarDto> map(List<CustomerCarEntity> source) {
		List<CustomerCarDto> rtn = new ArrayList<>();
		for (CustomerCarEntity entity : source) {
			CustomerCarDto dto = new CustomerCarDto();
			mapper.map(entity, dto);
			rtn.add(dto);
		}
		return rtn;
	}

	@Override
	public CustomerInfoDto findCustomerInfoByCusId(long cusId) throws Exception {
		CustomerInfoDto objCustomerInfoDto = new CustomerInfoDto();
		CustomerInfoEntity objCustomerInfoEntity = customerInfoRepository.findByCusId(cusId);
		mapper.map(objCustomerInfoEntity, objCustomerInfoDto);
		return objCustomerInfoDto;
	}

	@Override
	public CustomerDto findCustomerByOldId(long cusId) throws Exception {
		CustomerDto objCustomerDto = new CustomerDto();
		CustomerEntity objCustomerEntity = customerRepository.findByOldId(cusId);
		mapper.map(objCustomerEntity, objCustomerDto);
		return objCustomerDto;
	}

	@PostConstruct
	public void initialize() {
		mapper = new ModelMapper();
	}
}

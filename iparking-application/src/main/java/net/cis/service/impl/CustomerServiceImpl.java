package net.cis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import net.cis.common.util.constant.URLConstants;
import net.cis.common.util.constant.UserConstant;
import net.cis.dto.CustomerCarDto;
import net.cis.dto.CustomerDto;
import net.cis.dto.CustomerInfoDto;
import net.cis.jpa.entity.CustomerCarEntity;
import net.cis.jpa.entity.CustomerEntity;
import net.cis.jpa.entity.CustomerInfoEntity;
import net.cis.repository.CustomerCarRepository;
import net.cis.repository.CustomerInfoRepository;
import net.cis.repository.CustomerRepository;
import net.cis.service.CustomerService;
import net.cis.utils.RestfulUtil;

@Service
public class CustomerServiceImpl implements CustomerService {

	protected final Logger LOGGER = Logger.getLogger(getClass());

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	CustomerCarRepository customerCarRepository;

	@Autowired
	CustomerInfoRepository customerInfoRepository;

	ModelMapper mapper;

	@PostConstruct
	public void initialize() {
		mapper = new ModelMapper();
	}

	@Override
	public Map<String, Object> saveCustomerInPoseidonDb(String phone) throws Exception {
		// TODO Auto-generated method stub
		String finalURL = URLConstants.URL_CREATE_CUSTOMER;
		List<NameValuePair> formParams = new ArrayList<>();
		formParams.add(new BasicNameValuePair("phone", phone));
		String responseContent = RestfulUtil.postFormData(finalURL, formParams,
				MediaType.APPLICATION_FORM_URLENCODED_VALUE);
		LOGGER.info("createCustomerInPoseidonDb Response: " + responseContent);
		return parseJSonToCreatePoseidonResponseObject(responseContent);
	}

	private Map<String, Object> parseJSonToCreatePoseidonResponseObject(String dataResult) throws JSONException {
		Map<String, Object> result = new HashMap<String, Object>();
		JSONObject ticketJSon = new JSONObject(dataResult);
		JSONObject ticketErrorJSon = ticketJSon.getJSONObject("Error");
		JSONObject ticketDataJSon = ticketJSon.getJSONObject("Data");
		if (ticketErrorJSon.has("Code")) {
			result.put("Code", ticketErrorJSon.getString("Code"));
		}
		if (ticketErrorJSon.has("Message")) {
			result.put("Message", ticketErrorJSon.getString("Message"));
		}
		if (ticketDataJSon.has("Id")) {
			result.put("cus_id", ticketDataJSon.getLong("Id"));
		}
		if (ticketDataJSon.has("Phone")) {
			result.put("Phone", ticketDataJSon.getString("Phone"));
		}
		if (ticketDataJSon.has("Phone2")) {
			result.put("Phone2", ticketDataJSon.getString("Phone2"));
		}
		if (ticketDataJSon.has("Telco")) {
			result.put("Telco", ticketDataJSon.getString("Telco"));
		}
		if (ticketDataJSon.has("Password")) {
			result.put("Password", ticketDataJSon.getString("Password"));
		}
		if (ticketDataJSon.has("Status")) {
			result.put("Status", ticketDataJSon.getInt("Status"));
		}
		if (ticketDataJSon.has("Created_at")) {
			result.put("Created_at", ticketDataJSon.getString("Created_at"));
		}
		if (ticketDataJSon.has("Updated_at")) {
			result.put("Updated_at", ticketDataJSon.getString("Updated_at"));
		}
		if (ticketDataJSon.has("Checksum")) {
			result.put("Checksum", ticketDataJSon.getString("Checksum"));
		}

		return result;
	}

	@Override
	public CustomerDto saveCustomerInIparkingCenter(CustomerDto customerDto) {
		CustomerEntity objCustomerEntity = new CustomerEntity();
		mapper.map(customerDto, objCustomerEntity);
		mapper.map(customerRepository.save(objCustomerEntity), customerDto);
		return customerDto;
	}

	@Override
	public List<CustomerCarDto> findCustomerCarByNumberPlate(String numberPlate) throws Exception {
		List<CustomerCarEntity> lstCustomerCarEntity = customerCarRepository.findCustomerCarByNumberPlate(numberPlate);
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
		if (objCustomerInfoEntity == null)
			return null;
		mapper.map(objCustomerInfoEntity, objCustomerInfoDto);
		return objCustomerInfoDto;
	}

	@Override
	public CustomerDto findCustomerByOldId(long cusId) throws Exception {
		CustomerDto objCustomerDto = new CustomerDto();
		CustomerEntity objCustomerEntity = customerRepository.findByOldId(cusId);
		if (objCustomerEntity == null)
			return null;
		mapper.map(objCustomerEntity, objCustomerDto);
		return objCustomerDto;
	}

	@Override
	public CustomerDto findByPhone2(String phone) throws Exception {
		CustomerDto objCustomerDto = new CustomerDto();
		CustomerEntity objCustomerEntity = customerRepository.findByPhone2(phone);
		if (objCustomerEntity == null)
			return null;
		mapper.map(objCustomerEntity, objCustomerDto);
		return objCustomerDto;
	}

	@Override
	public CustomerInfoDto saveCustomerInfoEntity(CustomerInfoDto objCustomerInfoDto) throws Exception {
		CustomerInfoEntity objCustomerInfoEntity = new CustomerInfoEntity();
		mapper.map(objCustomerInfoDto, objCustomerInfoEntity);
		mapper.map(customerInfoRepository.save(objCustomerInfoEntity), objCustomerInfoDto);
		return objCustomerInfoDto;
	}

	@Override
	public CustomerCarDto saveCustomerCarEntity(CustomerCarDto objCustomerCarDto) throws Exception {
		CustomerCarEntity objCustomerCaEntity = new CustomerCarEntity();
		mapper.map(objCustomerCarDto, objCustomerCaEntity);
		mapper.map(customerCarRepository.save(objCustomerCaEntity), objCustomerCarDto);
		return objCustomerCarDto;
	}

	@Override
	public void saveCustomerInfoInPoseidonDb(long cusId, String phone, String email) throws Exception {
		// TODO Auto-generated method stub
		String finalURL = URLConstants.URL_CREATE_UPDATE_CUSTOMER_INFO;
		List<NameValuePair> formParams = new ArrayList<>();
		formParams.add(new BasicNameValuePair("customerID", String.valueOf(cusId)));
		formParams.add(new BasicNameValuePair("phone", phone));
		formParams.add(new BasicNameValuePair("email", email));
		String responseContent = RestfulUtil.postFormData(finalURL, formParams,
				MediaType.APPLICATION_FORM_URLENCODED_VALUE);
		LOGGER.info("saveCustomerInfoInPoseidonDb Response: " + responseContent);
	}

	@Override
	public CustomerCarDto findCustomerCarByNumberPlateAndCusId(String numberPlate, long cusId) throws Exception {
		CustomerCarDto objCustomerCarDto = new CustomerCarDto();
		CustomerCarEntity objCustomerCarEntity = customerCarRepository
				.findCustomerCarByNumberPlateAndCustomer(numberPlate, cusId);
		if (objCustomerCarEntity == null)
			return null;
		mapper.map(objCustomerCarEntity, objCustomerCarDto);
		return objCustomerCarDto;
	}

	@Override
	public CustomerCarDto findCustomerCarById(long id) throws Exception {
		CustomerCarDto objCustomerCarDto = new CustomerCarDto();
		CustomerCarEntity objCustomerCarEntity = customerCarRepository.findOne(id);
		if (objCustomerCarEntity == null)
			return null;
		mapper.map(objCustomerCarEntity, objCustomerCarDto);
		return objCustomerCarDto;
	}

	@Override
	public void deleteCustomerCar(long id) throws Exception {
		customerCarRepository.delete(id);
	}

	@Override
	public Map<String, Object> saveCustomerCarInPoseidonDb(long cusId, String numberPlate, int carType)
			throws Exception {
		// TODO Auto-generated method stub
		String finalURL = URLConstants.URL_CREATE_CUSTOMER_CAR;
		List<NameValuePair> formParams = new ArrayList<>();
		formParams.add(new BasicNameValuePair("customerID", String.valueOf(cusId)));
		formParams.add(new BasicNameValuePair("number_plate", numberPlate));
		formParams.add(new BasicNameValuePair("car_type", String.valueOf(carType)));
		String responseContent = RestfulUtil.postFormData(finalURL, formParams,
				MediaType.APPLICATION_FORM_URLENCODED_VALUE);
		LOGGER.info("createCustomerInPoseidonDb Response: " + responseContent);
		return parseJSonToSaveCustomerCarPoseidonResponseObject(responseContent);
	}

	private Map<String, Object> parseJSonToSaveCustomerCarPoseidonResponseObject(String dataResult)
			throws JSONException {
		Map<String, Object> result = new HashMap<String, Object>();
		JSONObject ticketJSon = new JSONObject(dataResult);
		JSONObject ticketErrorJSon = ticketJSon.getJSONObject("Error");
		JSONObject ticketDataJSon = ticketJSon.getJSONObject("Data");
		if (ticketErrorJSon.has("Code")) {
			result.put("Code", ticketErrorJSon.getString("Code"));
		}
		if (ticketErrorJSon.has("Message")) {
			result.put("Message", ticketErrorJSon.getString("Message"));
		}
		if (ticketDataJSon.has("Id")) {
			result.put("cus_car_id", ticketDataJSon.getLong("Id"));
		}
		if (ticketDataJSon.has("Cus_id")) {
			result.put("cus_id", ticketDataJSon.getLong("Cus_id"));
		}
		if (ticketDataJSon.has("Number_plate")) {
			result.put("number_plate", ticketDataJSon.getString("Number_plate"));
		}
		if (ticketDataJSon.has("Type_id")) {
			result.put("type_id", ticketDataJSon.getInt("Type_id"));
		}
		if (ticketDataJSon.has("Status")) {
			result.put("status", ticketDataJSon.getInt("Status"));
		}
		return result;
	}


	@Override
	public CustomerDto findById(long cusId) throws Exception {
		CustomerDto objCustomerDto = new CustomerDto();
		CustomerEntity objCustomerEntity = customerRepository.findById(cusId);
		if (objCustomerEntity == null)
			return null;
		mapper.map(objCustomerEntity, objCustomerDto);
		return objCustomerDto;
	}

}

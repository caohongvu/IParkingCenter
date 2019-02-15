package net.cis.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

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
import net.cis.constants.UserConstans;
import net.cis.dto.CustomerCarDto;
import net.cis.dto.CustomerDto;
import net.cis.dto.CustomerInfoDto;
import net.cis.dto.CustomerNotificationDto;
import net.cis.dto.MenuDto;
import net.cis.jpa.entity.CustomerCarEntity;
import net.cis.jpa.entity.CustomerEntity;
import net.cis.jpa.entity.CustomerInfoEntity;
import net.cis.jpa.entity.CustomerNotificationEntity;
import net.cis.repository.CustomerCarRepository;
import net.cis.repository.CustomerInfoRepository;
import net.cis.repository.CustomerNotificationRepository;
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

	@Autowired
	CustomerNotificationRepository customerNotificationRepository;

	ModelMapper mapper;

	@PostConstruct
	public void initialize() {
		mapper = new ModelMapper();
	}

	@Override
	public Map<String, Object> createCustomerInShardDb(String phone) throws Exception {
		// TODO Auto-generated method stub
		String finalURL = URLConstants.URL_CREATE_CUSTOMER;
		List<NameValuePair> formParams = new ArrayList<>();
		formParams.add(new BasicNameValuePair("phone", phone));
		String responseContent = RestfulUtil.postFormData(finalURL, formParams,
				MediaType.APPLICATION_FORM_URLENCODED_VALUE);
		LOGGER.info("createCustomerInShardDb Response: " + responseContent);
		return parseJSonToCreatePoseidonResponseObject(responseContent);
	}

	@Override
	public Map<String, Object> updateCustomerInShardDb(String cusId, String password) throws Exception {
		// TODO Auto-generated method stub
		String finalURL = URLConstants.URL_UPDATE_CUSTOMER;
		List<NameValuePair> formParams = new ArrayList<>();
		formParams.add(new BasicNameValuePair("customerID", cusId));
		formParams.add(new BasicNameValuePair("password", password));
		String responseContent = RestfulUtil.postFormData(finalURL, formParams,
				MediaType.APPLICATION_FORM_URLENCODED_VALUE);
		LOGGER.info("updateCustomerInShardDb Response: " + responseContent);
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
	public Map<String, Object> createCustomerCarInShardDb(long cusId, String numberPlate, int carType)
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
		if (ticketDataJSon.has("P_class")) {
			result.put("p_class", ticketDataJSon.getString("P_class"));
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

	@Override
	public Map<String, Object> saveCustomerInfoInPoseidonDbReturnObject(long cusId, String phone, String email)
			throws Exception {
		// TODO Auto-generated method stub
		String finalURL = URLConstants.URL_CREATE_UPDATE_CUSTOMER_INFO;
		List<NameValuePair> formParams = new ArrayList<>();
		formParams.add(new BasicNameValuePair("customerID", String.valueOf(cusId)));
		formParams.add(new BasicNameValuePair("phone", phone));
		formParams.add(new BasicNameValuePair("email", email));
		String responseContent = RestfulUtil.postFormData(finalURL, formParams,
				MediaType.APPLICATION_FORM_URLENCODED_VALUE);
		LOGGER.info("saveCustomerInfoInPoseidonDb Response: " + responseContent);
		Map<String, Object> result = new HashMap<String, Object>();
		JSONObject ticketJSon = new JSONObject(responseContent);
		JSONObject ticketErrorJSon = ticketJSon.getJSONObject("Error");
		JSONObject ticketDataJSon = ticketJSon.getJSONObject("Data");
		if (ticketErrorJSon.has("Code")) {
			result.put("Code", ticketErrorJSon.getString("Code"));
		}
		if (ticketErrorJSon.has("Message")) {
			result.put("Message", ticketErrorJSon.getString("Message"));
		}
		if (ticketDataJSon.has("Verification_code")) {
			result.put("VerificationCode", ticketDataJSon.getString("Verification_code"));
		}

		if (ticketDataJSon.has("Status")) {
			result.put("Status", ticketDataJSon.getInt("Status"));
		}

		return result;
	}

	@Override
	public InputStream getCapcha(String captchaID) throws Exception {
		LOGGER.info("getCapcha captchaID: " + captchaID);
		String finalURL = URLConstants.URL_CREATE_CAPCHA;
		finalURL = finalURL.replace("{capchaId}", captchaID);
		InputStream responseContent = RestfulUtil.getWithOutAccessToken(finalURL, null);
		LOGGER.info("getCapcha Response: " + responseContent);
		return responseContent;
	}

	@Override
	public Map<String, Object> otpSignupCallGolang(String phone, String captcha, String captchaID) throws Exception {
		LOGGER.info("otpSignupCallGolang phone: " + phone);
		LOGGER.info("otpSignupCallGolang captcha: " + captcha);
		LOGGER.info("otpSignupCallGolang captchaID: " + captchaID);
		// TODO Auto-generated method stub
		String finalURL = URLConstants.URL_OTP_SIGNUP;
		List<NameValuePair> formParams = new ArrayList<>();
		formParams.add(new BasicNameValuePair("phone", phone));
		formParams.add(new BasicNameValuePair("captchaID", captchaID));
		formParams.add(new BasicNameValuePair("captcha", captcha));
		String responseContent = RestfulUtil.postFormData(finalURL, formParams,
				MediaType.APPLICATION_FORM_URLENCODED_VALUE);
		LOGGER.info("otpSignupCallGolang Response: " + responseContent);

		Map<String, Object> result = new HashMap<String, Object>();
		JSONObject ticketJSon = new JSONObject(responseContent);
		JSONObject ticketErrorJSon = ticketJSon.getJSONObject("Error");
		if (ticketErrorJSon.has("Code")) {
			result.put("Code", ticketErrorJSon.getString("Code"));
		}
		if (ticketErrorJSon.has("Message")) {
			result.put("Message", ticketErrorJSon.getString("Message"));
		}
		if (!ticketJSon.isNull("Data")) {
			JSONObject ticketDataJSon = ticketJSon.getJSONObject("Data");
			result.put("Ticket", ticketDataJSon.getString("Ticket"));
		}
		return result;
	}

	@Override
	public Map<String, Object> verifyOtpSignupCallGolang(String phone, String otp, String ticket) throws Exception {
		LOGGER.info("napSignupCallGolang phone: " + phone);
		LOGGER.info("napSignupCallGolang ticket: " + ticket);
		LOGGER.info("napSignupCallGolang otp: " + otp);
		// TODO Auto-generated method stub
		String finalURL = URLConstants.URL_VERIFY_OTP_SIGNUP;
		List<NameValuePair> formParams = new ArrayList<>();
		formParams.add(new BasicNameValuePair("phone", phone));
		formParams.add(new BasicNameValuePair("ticket", ticket));
		formParams.add(new BasicNameValuePair("otp", otp));
		String responseContent = RestfulUtil.postFormData(finalURL, formParams,
				MediaType.APPLICATION_FORM_URLENCODED_VALUE);
		LOGGER.info("verifyOtpSignupCallGolang Response: " + responseContent);
		Map<String, Object> result = new HashMap<String, Object>();
		JSONObject ticketJSon = new JSONObject(responseContent);
		JSONObject ticketErrorJSon = ticketJSon.getJSONObject("Error");
		if (ticketErrorJSon.has("Code")) {
			result.put("Code", ticketErrorJSon.getString("Code"));
		}
		if (ticketErrorJSon.has("Message")) {
			result.put("Message", ticketErrorJSon.getString("Message"));
		}
		return result;
	}

	@Override
	public Map<String, Object> napSignupCallGolang(String phone, String password) throws Exception {
		LOGGER.info("napSignupCallGolang phone: " + phone);
		LOGGER.info("napSignupCallGolang password: " + password);
		// TODO Auto-generated method stub
		String finalURL = URLConstants.URL_NAP_SIGNUP;
		List<NameValuePair> formParams = new ArrayList<>();
		formParams.add(new BasicNameValuePair("phone", phone));
		formParams.add(new BasicNameValuePair("password", password));
		String responseContent = RestfulUtil.postFormData(finalURL, formParams,
				MediaType.APPLICATION_FORM_URLENCODED_VALUE);
		LOGGER.info("napSignupCallGolang Response: " + responseContent);

		Map<String, Object> result = new HashMap<String, Object>();
		JSONObject ticketJSon = new JSONObject(responseContent);
		JSONObject ticketErrorJSon = ticketJSon.getJSONObject("Error");

		if (ticketErrorJSon.has("Code")) {
			result.put("Code", ticketErrorJSon.getString("Code"));
		}
		if (ticketErrorJSon.has("Message")) {
			result.put("Message", ticketErrorJSon.getString("Message"));
		}

		if (!ticketJSon.isNull("Data")) {
			JSONObject ticketDataJSon = ticketJSon.getJSONObject("Data");
			if (ticketDataJSon.has("token")) {
				result.put("token", ticketDataJSon.getString("token"));
			}
			if (ticketDataJSon.has("Token")) {
				result.put("Token", ticketDataJSon.getString("Token"));
			}
			if (ticketDataJSon.has("cus_id")) {
				result.put("cus_id", ticketDataJSon.getLong("cus_id"));
			}
			if (ticketDataJSon.has("checksum")) {
				result.put("checksum", ticketDataJSon.getString("checksum"));
			}
			if (ticketDataJSon.has("telco")) {
				result.put("telco", ticketDataJSon.getString("telco"));
			}
		}
		return result;
	}

	@Override
	public CustomerInfoDto findCustomerInfoByEmail(String email) throws Exception {
		CustomerInfoEntity entity = customerInfoRepository.findByEmailIgnoreCase(email);
		CustomerInfoDto dto = new CustomerInfoDto();
		mapper.map(entity, dto);
		return dto;
	}

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<MenuDto> getMenuByRoleForWeb(Integer roleId) {
		StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_menu");
		storedProcedureQuery.registerStoredProcedureParameter("role", Integer.class, ParameterMode.IN);
		storedProcedureQuery.registerStoredProcedureParameter("type_func", Integer.class, ParameterMode.IN);
		storedProcedureQuery.setParameter("role", roleId);
		storedProcedureQuery.setParameter("type_func", UserConstans.FUNC_TYPE_WEB);
		storedProcedureQuery.execute();
		List<MenuDto> result = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<Object[]> lst = storedProcedureQuery.getResultList();
		Integer temp_parent = 0;
		int start = 0;
		MenuDto objMenuDto = new MenuDto();
		List<MenuDto> menuChilds = new ArrayList<MenuDto>();
		for (Object[] value : lst) {
			if (temp_parent != (int) value[0] && temp_parent != 0) {
				objMenuDto.setMenuChilds(menuChilds);
				result.add(objMenuDto);

				objMenuDto = new MenuDto();
				menuChilds = new ArrayList<MenuDto>();
			}

			objMenuDto.setId((int) value[0]);
			objMenuDto.setName(value[1] != null ? value[1].toString() : null);
			objMenuDto.setLabel(value[2] != null ? value[2].toString() : null);
			objMenuDto.setDescription(value[3] != null ? value[3].toString() : null);
			objMenuDto.setLevel((int) value[4]);
			objMenuDto.setIcon(value[5] != null ? value[5].toString() : null);
			objMenuDto.setLink(value[6] != null ? value[6].toString() : null);

			MenuDto menuChild = new MenuDto();
			menuChild.setId((int) value[7]);
			menuChild.setName(value[8] != null ? value[8].toString() : null);
			menuChild.setLabel(value[9] != null ? value[9].toString() : null);
			menuChild.setDescription(value[10] != null ? value[10].toString() : null);
			menuChild.setLevel((int) value[11]);
			menuChild.setParent_id((int) value[0]);
			menuChild.setIcon(value[12] != null ? value[12].toString() : null);
			menuChild.setLink(value[13] != null ? value[13].toString() : null);
			menuChilds.add(menuChild);

			if (start == lst.size() - 1) {
				objMenuDto.setMenuChilds(menuChilds);
				result.add(objMenuDto);
			}
			start++;
			temp_parent = (int) value[0];
		}
		return result;
	}
	
	
	@Override
	public List<MenuDto> getMenuCustomerByRoleForWeb(Integer roleId) {
		StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_menu_customer");
		storedProcedureQuery.registerStoredProcedureParameter("role", Integer.class, ParameterMode.IN);
		storedProcedureQuery.registerStoredProcedureParameter("type_func", Integer.class, ParameterMode.IN);
		storedProcedureQuery.setParameter("role", roleId);
		storedProcedureQuery.setParameter("type_func", UserConstans.FUNC_TYPE_WEB);
		storedProcedureQuery.execute();
		List<MenuDto> result = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<Object[]> lst = storedProcedureQuery.getResultList();
		Integer temp_parent = 0;
		int start = 0;
		MenuDto objMenuDto = new MenuDto();
		List<MenuDto> menuChilds = new ArrayList<MenuDto>();
		for (Object[] value : lst) {
			if (temp_parent != (int) value[0] && temp_parent != 0) {
				objMenuDto.setMenuChilds(menuChilds);
				result.add(objMenuDto);

				objMenuDto = new MenuDto();
				menuChilds = new ArrayList<MenuDto>();
			}

			objMenuDto.setId((int) value[0]);
			objMenuDto.setName(value[1] != null ? value[1].toString() : null);
			objMenuDto.setLabel(value[2] != null ? value[2].toString() : null);
			objMenuDto.setDescription(value[3] != null ? value[3].toString() : null);
			objMenuDto.setLevel((int) value[4]);
			objMenuDto.setIcon(value[5] != null ? value[5].toString() : null);
			objMenuDto.setLink(value[6] != null ? value[6].toString() : null);

			MenuDto menuChild = new MenuDto();
			menuChild.setId((int) value[7]);
			menuChild.setName(value[8] != null ? value[8].toString() : null);
			menuChild.setLabel(value[9] != null ? value[9].toString() : null);
			menuChild.setDescription(value[10] != null ? value[10].toString() : null);
			menuChild.setLevel((int) value[11]);
			menuChild.setParent_id((int) value[0]);
			menuChild.setIcon(value[12] != null ? value[12].toString() : null);
			menuChild.setLink(value[13] != null ? value[13].toString() : null);
			menuChilds.add(menuChild);

			if (start == lst.size() - 1) {
				objMenuDto.setMenuChilds(menuChilds);
				result.add(objMenuDto);
			}
			start++;
			temp_parent = (int) value[0];
		}
		return result;
	}


	@Override
	public List<CustomerNotificationDto> findCustomerNotificationByCusId(long cusId, Integer subscrice)
			throws Exception {
		List<CustomerNotificationEntity> entity = customerNotificationRepository.findByCusIdAndSubscrice(cusId,
				subscrice);
		if (entity == null) {
			return null;
		}
		return this.mapCustomerNotification(entity);
	}

	private List<CustomerNotificationDto> mapCustomerNotification(List<CustomerNotificationEntity> source) {
		List<CustomerNotificationDto> rtn = new ArrayList<>();
		for (CustomerNotificationEntity entity : source) {
			CustomerNotificationDto dto = new CustomerNotificationDto();
			mapper.map(entity, dto);
			rtn.add(dto);
		}
		return rtn;
	}

}

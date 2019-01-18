package net.cis.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.cis.common.util.MessageUtil;
import net.cis.common.util.Utils;
import net.cis.constants.ResponseErrorCodeConstants;
import net.cis.dto.AccountUserDto;
import net.cis.dto.ErrorDto;
import net.cis.dto.ResponseApi;
import net.cis.dto.UserDto;
import net.cis.jpa.entity.UserEntity;
import net.cis.jpa.entity.UserMetadataEntity;
import net.cis.jpa.entity.UserSecurityEntity;
import net.cis.repository.UserMetadataRepository;
import net.cis.repository.UserRepository;
import net.cis.repository.UserSecurityRepository;
import net.cis.service.UserService;
import net.cis.utils.RestfulUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserSecurityRepository userSecurityRepository;

	@Autowired
	private UserMetadataRepository userMetadataRepository;

	ModelMapper mapper;

	private List<AccountUserDto> map(List<UserEntity> source) {

		ArrayList<AccountUserDto> rtn = new ArrayList<>();
		source.stream().map((entity) -> {
			AccountUserDto dto = new AccountUserDto();
			mapper.map(entity, dto);
			if (entity.getUserMetadata() != null) {
				dto.setEmail(entity.getUserMetadata().getEmail());
				dto.setPhone(entity.getUserMetadata().getPhone());
			}
			if (entity.getUserSecurityEntity() != null) {
				dto.setGr(entity.getUserSecurityEntity().getGr());
				dto.setRole(entity.getUserSecurityEntity().getRole());
			}

			return dto;
		}).forEachOrdered((dto) -> {
			rtn.add(dto);
		});
		return rtn;
	}

	@PostConstruct
	public void initialize() {
		mapper = new ModelMapper();
	}

	@Override
	public ResponseApi findAll(String username, String fullname, Pageable pageable) {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		List<UserEntity> userEntities = null;
		try {
			userEntities = userRepository.getAll(username, fullname, pageable);
			if (userEntities == null) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
				errorDto.setMessage("");
				responseApi.setError(errorDto);
				return responseApi;
			}
			List<AccountUserDto> userDto = this.map(userEntities);
			responseApi.setData(userDto);
			errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
			errorDto.setMessage("");
			responseApi.setError(errorDto);
			return responseApi;

		} catch (Exception e) {
			System.out.println(e.getMessage());
			errorDto.setCode(ResponseErrorCodeConstants.StatusInternalServerError);
			errorDto.setMessage(ResponseErrorCodeConstants.DBAccessErr);
			responseApi.setError(errorDto);
			return responseApi;
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseApi save(UserDto userDto) throws JSONException {

		String createUserURL = "https://admapi.live.iparkingstg.com/p/create/account";
		// String createUserURL = "http://localhost:8800/p/create/account";

		List<NameValuePair> formParams = new ArrayList<>();

		ResponseApi respointApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();

		// check username ton tai
		UserEntity userExist = userRepository.findByUsername(userDto.getUsername());
		if (userExist != null) {
			errorDto.setCode(400);
			errorDto.setMessage("Tài khoản đã tồn tại");
			respointApi.setData(null);
			respointApi.setError(errorDto);
			return respointApi;
		}

		// check phone
		if (!Utils.validateVNPhoneNumber(userDto.getPhone())) {
			errorDto.setCode(400);
			errorDto.setMessage(MessageUtil.MESSAGE_PHONE_WRONG_FORMAT);
			respointApi.setData(null);
			respointApi.setError(errorDto);
			return respointApi;
		}
		String phone = Utils.buildPhoneNumberWithPrefix(userDto.getPhone());
		userDto.setPhone(phone);

		// check email
		if (!Utils.validateEmail(userDto.getEmail())) {
			errorDto.setCode(400);
			errorDto.setMessage("Email sai định dạng");
			respointApi.setData(null);
			respointApi.setError(errorDto);
			return respointApi;
		}

		formParams.add(new BasicNameValuePair("username", userDto.getUsername()));
		formParams.add(new BasicNameValuePair("fullname", userDto.getFullname()));
		formParams.add(new BasicNameValuePair("password", userDto.getPassword()));
		formParams.add(new BasicNameValuePair("role", String.valueOf(userDto.getRole())));
		formParams.add(new BasicNameValuePair("group", "2"));
		formParams.add(new BasicNameValuePair("delegatepayment", "0"));

		// create user at db golang
		String responseContent = RestfulUtil.postFormData(createUserURL, formParams,
				MediaType.APPLICATION_FORM_URLENCODED_VALUE);
		JSONObject jsonObject = new JSONObject(responseContent);

		String error = jsonObject.getJSONObject("Error").getString("Code");
		String message = jsonObject.getJSONObject("Error").getString("Message");

		if (Integer.parseInt(error) != 200) {
			errorDto.setCode(Integer.parseInt(error));
			errorDto.setMessage(message);
			respointApi.setData(null);
			respointApi.setError(errorDto);

		} else {
			// get checksum and password from golang
			String checksumUser = jsonObject.getJSONObject("Data").getString("ChecksumUser");
			String checksumSecu = jsonObject.getJSONObject("Data").getString("ChecksumSecu");
			String password = jsonObject.getJSONObject("Data").getString("Password");
			String recovery = jsonObject.getJSONObject("Data").getString("Recovery");
			int type = Integer.parseInt(jsonObject.getJSONObject("Data").getString("Type"));
			int id = Integer.parseInt(jsonObject.getJSONObject("Data").getString("Id"));

			// security
			// user

			userDto.setType(type);
			userDto.setPassword(password);
			userDto.setRecovery(recovery);

			UserEntity entity = new UserEntity();
			UserSecurityEntity securityEntity = new UserSecurityEntity();
			UserMetadataEntity metadataEntity = new UserMetadataEntity();

			// user
			mapper.map(userDto, entity);
			entity.setChecksum(checksumUser);
			entity.setId(id);
			UserEntity res = userRepository.save(entity);

			// security
			mapper.map(userDto, securityEntity);
			securityEntity.setChecksum(checksumSecu);
			userSecurityRepository.save(securityEntity);

			// metadata
			mapper.map(userDto, metadataEntity);
			metadataEntity.setId(res.getId());
			metadataEntity.setEmail_verifed(0);
			metadataEntity.setEmail_veryfication_code("");
			userMetadataRepository.save(metadataEntity);

			errorDto.setCode(200);
			errorDto.setMessage("Success");
			respointApi.setData(null);
			respointApi.setError(errorDto);

		}

		return respointApi;
	}

	// FIND BY ID
	@Override
	public ResponseApi findById(int Id) {
		UserEntity entity = new UserEntity();
		ResponseApi responseApi = new ResponseApi();

		entity = userRepository.findOne(Id);
		if (entity == null) {
			responseApi.setData(null);
		}
		responseApi.setData(entity);
		return responseApi;
	}

	// FIND BY USERNAME
	@Override
	public UserEntity findByUsername(String username) {
		UserEntity entity = new UserEntity();
		entity = userRepository.findByUsername(username);

		return entity;
	}

	// UPDATE
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseApi update(UserDto userDto) throws JSONException {

		String updateUserURL = "https://admapi.live.iparkingstg.com/p/update/account";
		// String updateUserURL = "http://localhost:8800/p/update/account";

		List<NameValuePair> formParams = new ArrayList<>();

		ResponseApi respointApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();

		// check phone
		if (!Utils.validateVNPhoneNumber(userDto.getPhone())) {
			errorDto.setCode(400);
			errorDto.setMessage(MessageUtil.MESSAGE_PHONE_WRONG_FORMAT);
			respointApi.setData(null);
			respointApi.setError(errorDto);
			return respointApi;
		}
		String phone = Utils.buildPhoneNumberWithPrefix(userDto.getPhone());
		userDto.setPhone(phone);

		// check email
		if (!Utils.validateEmail(userDto.getEmail())) {
			errorDto.setCode(400);
			errorDto.setMessage("Email sai định dạng");
			respointApi.setData(null);
			respointApi.setError(errorDto);
			return respointApi;
		}

		formParams.add(new BasicNameValuePair("username", userDto.getUsername()));
		formParams.add(new BasicNameValuePair("fullname", userDto.getFullname()));
		formParams.add(new BasicNameValuePair("password", userDto.getPassword()));
		formParams.add(new BasicNameValuePair("role", String.valueOf(userDto.getRole())));
		formParams.add(new BasicNameValuePair("group", "2"));
		formParams.add(new BasicNameValuePair("delegatepayment", "1"));
		formParams.add(new BasicNameValuePair("id", String.valueOf(userDto.getId())));

		String responseContent = RestfulUtil.postFormData(updateUserURL, formParams,
				MediaType.APPLICATION_FORM_URLENCODED_VALUE);
		JSONObject jsonObject = new JSONObject(responseContent);

		String error = jsonObject.getJSONObject("Error").getString("Code");
		String message = jsonObject.getJSONObject("Error").getString("Message");

		UserEntity userExits = userRepository.findOne(userDto.getId());

		if (Integer.parseInt(error) != 200) {
			errorDto.setCode(Integer.parseInt(error));
			errorDto.setMessage(message);
			respointApi.setData(null);
			respointApi.setError(errorDto);

		} else {
			// get checksum and password from golang
			String checksumUser = jsonObject.getJSONObject("Data").getString("ChecksumUser");
			String checksumSecu = jsonObject.getJSONObject("Data").getString("ChecksumSecu");
			String password = jsonObject.getJSONObject("Data").getString("Password");
			String recovery = jsonObject.getJSONObject("Data").getString("Recovery");
			int type = Integer.parseInt(jsonObject.getJSONObject("Data").getString("Type"));
			int id = Integer.parseInt(jsonObject.getJSONObject("Data").getString("Id"));

			// security
			// user

			userDto.setType(type);
			userDto.setPassword(password);
			userDto.setRecovery(recovery);

			UserEntity entity = new UserEntity();
			UserSecurityEntity securityEntity = new UserSecurityEntity();
			UserMetadataEntity metadataEntity = new UserMetadataEntity();

			// user
			mapper.map(userDto, entity);
			entity.setChecksum(checksumUser);
			entity.setId(id);
			entity.setStatus(1);
			entity.setPwd_counter(5);
			entity.setCreated_at(userExits.getCreated_at());
			UserEntity res = userRepository.save(entity);

			// security
			mapper.map(userDto, securityEntity);
			securityEntity.setChecksum(checksumSecu);
			securityEntity.setCreated_at(userExits.getCreated_at());
			userSecurityRepository.save(securityEntity);

			// metadata
			mapper.map(userDto, metadataEntity);
			metadataEntity.setId(res.getId());
			metadataEntity.setEmail_verifed(0);
			metadataEntity.setEmail_veryfication_code("");
			userMetadataRepository.save(metadataEntity);

			errorDto.setCode(200);
			errorDto.setMessage("Success");
			respointApi.setData(null);
			respointApi.setError(errorDto);

		}

		return respointApi;
	}

	@Override
	public UserDto findUserById(int Id) {
		UserDto objUserDto = new UserDto();
		UserEntity objUserEntity = userRepository.findOne(Id);
		if (objUserEntity == null) {
			return null;
		}
		mapper.map(objUserEntity, objUserDto);
		return objUserDto;
	}

}

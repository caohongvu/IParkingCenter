package net.cis.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.cis.constants.ResponseErrorCodeConstants;
import net.cis.dto.AccountUserDto;
import net.cis.dto.ErrorDto;
import net.cis.dto.ParkingDto;
import net.cis.dto.ResponseApi;
import net.cis.dto.TicketDto;
import net.cis.dto.UserDto;
import net.cis.jpa.entity.ParkingContractEntity;
import net.cis.jpa.entity.TicketEntity;
import net.cis.jpa.entity.UserEntity;
import net.cis.repository.UserRepository;
import net.cis.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	ModelMapper mapper;

	
	
	private List<AccountUserDto> map(List<UserEntity> source) {

		ArrayList<AccountUserDto> rtn = new ArrayList<>();
		source.stream().map((entity) -> {
			AccountUserDto dto = new AccountUserDto();
			mapper.map(entity, dto);
			if (entity.getUserMetadata() != null) {
				dto.setEmail(entity.getUserMetadata().getEmail());
			}
			if(entity.getUserSecurityEntity() != null) {
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
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
			errorDto.setCode(ResponseErrorCodeConstants.StatusInternalServerError);
			errorDto.setMessage(ResponseErrorCodeConstants.DBAccessErr);
			responseApi.setError(errorDto);
			return responseApi;
		}
	}
	@Override
	public ResponseApi save(UserDto userDto) {
		UserEntity entity = new UserEntity();
		mapper.map(userDto, entity);
		userRepository.save(entity);

		mapper.map(entity, userDto);
		ResponseApi api = new ResponseApi();
		return api;
	}
	//FIND BY ID
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
	
	//FIND BY USERNAME
	@Override
	public UserEntity findByUsername(String username) {
		UserEntity entity = new UserEntity();
		entity = userRepository.findByUsername(username);

		return entity;
	}


}

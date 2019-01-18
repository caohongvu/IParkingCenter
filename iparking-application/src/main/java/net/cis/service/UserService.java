package net.cis.service;

import org.json.JSONException;
import org.springframework.data.domain.Pageable;

import net.cis.dto.ResponseApi;
import net.cis.dto.UserDto;
import net.cis.jpa.entity.UserEntity;

public interface UserService {
	
	ResponseApi findAll(String username, String fullname,Pageable pageable);
	ResponseApi save(UserDto userDto) throws JSONException;
	
	ResponseApi findById(int Id);
	UserEntity findByUsername(String username);
	
	ResponseApi update(UserDto userDto) throws JSONException;

	UserDto findUserById(int Id) ;
}

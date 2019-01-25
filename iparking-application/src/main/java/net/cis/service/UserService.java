package net.cis.service;

import java.util.HashSet;

import org.json.JSONException;
import org.springframework.data.domain.Pageable;

import net.cis.dto.ResponseApi;
import net.cis.dto.UserDto;
import net.cis.jpa.entity.UserEntity;

public interface UserService {

	ResponseApi findAll(String username, String fullname, Pageable pageable);

	ResponseApi save(UserDto userDto) throws JSONException;

	UserDto findById(int Id);

	UserEntity findByUsername(String username);

	ResponseApi update(UserDto userDto) throws JSONException;

	ResponseApi findByUsername(HashSet<Integer> listIdUser, String username, String fullname);

	UserDto findUserById(int Id);

	ResponseApi resetPassWord(UserDto userDto) throws JSONException;

}

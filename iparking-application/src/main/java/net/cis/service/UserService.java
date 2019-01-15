package net.cis.service;

import org.springframework.data.domain.Pageable;
import net.cis.dto.ResponseApi;
import net.cis.dto.UserDto;
import net.cis.jpa.entity.UserEntity;

public interface UserService {
	
	ResponseApi findAll(String username, String fullname,Pageable pageable);
	ResponseApi save(UserDto userDto);
	
	ResponseApi findById(int Id);
	UserEntity findByUsername(String username);


}

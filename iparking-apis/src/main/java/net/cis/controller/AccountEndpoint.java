package net.cis.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.cis.common.web.BaseEndpoint;
import net.cis.constants.ResponseErrorCodeConstants;
import net.cis.dto.AccountUserDto;
import net.cis.dto.ErrorDto;
import net.cis.dto.ResponseApi;
import net.cis.dto.UserDto;
import net.cis.jpa.entity.UserEntity;
import net.cis.service.UserService;

@RestController
@RequestMapping("/account")
@Api(value = "config Endpoint", description = "The URL to handle config endpoint")
public class AccountEndpoint extends BaseEndpoint {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ApiOperation("Select all account by username and fullname")
	public @ResponseBody Object selectAllAccount(@RequestParam(name = "username", required = false) String username,
			@RequestParam(name = "fullname", required = false) String fullname,
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@RequestParam(name = "size", required = false, defaultValue = "500") int size) throws Exception {
		page = page - 1;
		if (page < 0) {
			page = 0;
		}
		Pageable pageable = new PageRequest(page, size);

		ResponseApi enpoint = userService.findAll(username, fullname, pageable);
		return enpoint;
	}

	// create Account User
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	@ApiOperation("Create account user")
	public @ResponseBody Object createAccountUser(@RequestParam(name = "username", required = true) String username,
			@RequestParam(name = "fullname", required = true) String fullname,
			@RequestParam(name = "email", required = true) String email,
			@RequestParam(name = "phone", required = false) String phone,
			@RequestParam(name = "password", required = true) String password,
			@RequestParam(name = "role", required = true) int role) throws Exception {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		
		ResponseApi endpoint = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		
		
 
		
		String createdAt = formatter.format(date);
		
		
		//check username da ton tai
		UserEntity user = userService.findByUsername(username);
		if (user != null) {
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage("username đã tồn tại!");
			endpoint.setError(errorDto);
			endpoint.setData(null);
		}else {
			UserDto userDto = new UserDto();
			userDto.setFullname(fullname);
			userDto.setUsername(username);
			userDto.setStatus(1);
			userDto.setCreated_at(createdAt);
			userDto.setUpdated_at(createdAt);
			userDto.setChecksum(1233456777);
			userDto.setPwd_counter(5);
			userDto.setType(2);
			endpoint = userService.save(userDto);
		}


		return endpoint;

	}

}

package net.cis.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.cis.common.util.Utils;
import net.cis.common.web.BaseEndpoint;
import net.cis.dto.ErrorDto;
import net.cis.dto.ParkingDto;
import net.cis.dto.ResponseApi;
import net.cis.dto.UserDto;
import net.cis.security.filter.TokenAuthenticationService;
import net.cis.service.ParkingActorService;
import net.cis.service.ParkingService;
import net.cis.service.UserService;
import net.cis.service.cache.ParkingPlaceCache;
import net.cis.jpa.entity.ParkingActorEntity;
import net.cis.jpa.entity.ParkingEntity;
import net.cis.jpa.entity.UserEntity;

@RestController
@RequestMapping("/account")
@Api(value = "config Endpoint", description = "The URL to handle config endpoint")
public class AccountEndpoint extends BaseEndpoint {

	@Autowired
	UserService userService;
	
	@Autowired
	ParkingActorService parkingActorService;
	
	
	@Autowired
	ParkingService parkingService;


	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ApiOperation("Select all account by username and fullname")
	public @ResponseBody Object selectAllAccount(HttpServletRequest request,@RequestParam(name = "username", required = false) String username,
			@RequestParam(name = "fullname", required = false) String fullname,
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@RequestParam(name = "size", required = false, defaultValue = "500") int size) throws Exception {
		
		page = page - 1;
		if (page < 0) {
			page = 0;
		}
		Pageable pageable = new PageRequest(page, size);
		
		String userId = TokenAuthenticationService.getAuthenticationInfo(request);
//		ResponseApi user = userService.findById(Integer.parseInt(userId));
		
		//lay danh sach diem do thuoc cong ty
		List<ParkingDto> listParkingPlace = parkingService.findByCompany(Long.parseLong(userId));
		HashSet<Integer> listIdUser = new HashSet<Integer>();

		for(int i = 0; i < listParkingPlace.size();i++) {
			List<ParkingActorEntity> listActor = parkingActorService.findByCppId(Long.parseLong(listParkingPlace.get(i).getOldId()));
			for (int j = 0; j < listActor.size();j++) {
				listIdUser.add(Math.toIntExact(listActor.get(j).getActor()));
			}
		}
		ResponseApi endpoint = null;
		if (Integer.parseInt(userId) == 472 || Integer.parseInt(userId) == 5) {
			 endpoint = userService.findAll(username, fullname, pageable);
		}else {
			 endpoint = userService.findByUsername(listIdUser,username,fullname);
		}

		return endpoint;
	}

	// create Account User
	@RequestMapping(value = "/create",headers = "Content-Type= multipart/form-data", method = RequestMethod.POST)
	@ApiOperation("Create account user")
	public @ResponseBody Object createAccountUser(@RequestParam(name = "username", required = false) String username,
			@RequestParam(name = "fullname", required = false) String fullname,
			@RequestParam(name = "email", required = false) String email,
			@RequestParam(name = "phone", required = false) String phone,
			@RequestParam(name = "password", required = false) String password,
			@RequestParam(name = "role", required = false) int role) throws Exception {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		ResponseApi endpoint = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();

		String createdAt = formatter.format(date);

		UserDto userDto = new UserDto();
		userDto.setFullname(fullname);
		userDto.setUsername(username);
		userDto.setStatus(1);
		userDto.setCreated_at(createdAt);
		userDto.setUpdated_at(createdAt);
		userDto.setPwd_counter(5);
		userDto.setType(2);
		userDto.setPassword(password);
		userDto.setPhone(phone);
		userDto.setEmail(email);
		userDto.setRole(role);
		endpoint = userService.save(userDto);

		return endpoint;

	}

	// update Account User
	@RequestMapping(value = "/update",headers = "Content-Type= multipart/form-data", method = RequestMethod.POST)
	@ApiOperation(" account user")
	public @ResponseBody Object updateAccountUser(@RequestParam(name = "username", required = false) String username,
			@RequestParam(name = "fullname", required = false) String fullname,
			@RequestParam(name = "email", required = false) String email,
			@RequestParam(name = "phone", required = false) String phone,
			@RequestParam(name = "password", required = false) String password,
			@RequestParam(name = "role", required = false) int role, @RequestParam(name = "id", required = false) int id)
			throws Exception {


		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		String updateAt = formatter.format(date);

		UserDto userDto = new UserDto();
		userDto.setFullname(fullname);
		userDto.setUsername(username);
		userDto.setUpdated_at(updateAt);
		userDto.setPassword(password);
		userDto.setPhone(phone);
		userDto.setEmail(email);
		userDto.setId(id);
		userDto.setRole(role);
		
		ResponseApi endpoint = userService.update(userDto);

		return endpoint;

	}

}

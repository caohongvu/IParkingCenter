package net.cis.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.cis.common.util.MessageUtil;
import net.cis.common.util.PasswordGenerator;
import net.cis.common.web.BaseEndpoint;
import net.cis.constants.UserConstans;
import net.cis.dto.MenuDto;
import net.cis.dto.ResponseDto;
import net.cis.dto.RoleDto;
import net.cis.dto.UserDto;
import net.cis.dto.UserInfo;
import net.cis.dto.UserSecurityDto;
import net.cis.security.filter.TokenAuthenticationService;
import net.cis.service.RoleService;
import net.cis.service.UserSecurityService;
import net.cis.service.UserService;

@RestController
@RequestMapping("/user")
@Api(value = "user Endpoint", description = "The URL to handle user endpoint")
public class UserEndpoint extends BaseEndpoint {
	protected final Logger LOGGER = Logger.getLogger(getClass());
	@Autowired
	UserSecurityService userSecurityService;

	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	/**
	 * liemnh customer dang nhap he thong
	 * 
	 * @param phone
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/web/signin", method = RequestMethod.GET)
	@ApiOperation("signup user")
	public @ResponseBody ResponseDto webSignIn(@RequestParam(name = "username") String username,
			@RequestParam(name = "password") String password) {
		ResponseDto responseDto = new ResponseDto();
		try {
			// thuc hien tim kiem theo username
			UserSecurityDto objUserSecurityDto = userSecurityService.findByUsername(username);
			if (objUserSecurityDto == null) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage(MessageUtil.MESSAGE_CUSTOMER_NOT_EXITS);
				return responseDto;
			}
			// thuc hien verify pass
			if (!PasswordGenerator.verifyPassword(password, new String(objUserSecurityDto.getPassword()))) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage(MessageUtil.MESSAGE_CUSTOMER_WRONG_PASS);
				return responseDto;
			}
			UserDto objUserDto = userService.findByUsername(username);
			if (UserConstans.USER_DISABLE == objUserDto.getStatus()) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage(MessageUtil.MESSAGE_USER_LOCK);
				return responseDto;
			}
			// lay thong tin role
			RoleDto roleDto = roleService.findOne(Long.parseLong(String.valueOf(objUserSecurityDto.getRole())));
			if (UserConstans.USER_ROLE_DISABLE == roleDto.getStatus()) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage(MessageUtil.MESSAGE_USER_ROLE_LOCK);
				return responseDto;
			}
			responseDto.setCode(HttpStatus.OK.toString());
			UserInfo userInfo = new UserInfo();
			userInfo.setToken(TokenAuthenticationService.createTokenUser(String.valueOf(objUserDto.getId()),
					objUserSecurityDto.getGr(), objUserSecurityDto.getRole(),
					objUserSecurityDto.getRoleDelegatePayment() == 1 ? Boolean.TRUE : Boolean.FALSE));
			List<MenuDto> menus = userSecurityService.getMenuByRoleForWeb(objUserSecurityDto.getRole());
			userInfo.setMenus(menus);
			responseDto.setData(userInfo);
			return responseDto;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			LOGGER.error("Lỗi hệ thống: " + e.getMessage());
			responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
			return responseDto;
		}
	}

	/**
	 * liemnh customer dang nhap he thong
	 * 
	 * @param phone
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/app/signin", method = RequestMethod.GET)
	@ApiOperation("signup user")
	public @ResponseBody ResponseDto appSignIn(@RequestParam(name = "username") String username,
			@RequestParam(name = "password") String password) {
		ResponseDto responseDto = new ResponseDto();
		try {
			// thuc hien tim kiem theo username
			UserSecurityDto objUserSecurityDto = userSecurityService.findByUsername(username);
			if (objUserSecurityDto == null) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage(MessageUtil.MESSAGE_CUSTOMER_NOT_EXITS);
				return responseDto;
			}
			// thuc hien verify pass
			if (!PasswordGenerator.verifyPassword(password, new String(objUserSecurityDto.getPassword()))) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage(MessageUtil.MESSAGE_CUSTOMER_WRONG_PASS);
				return responseDto;
			}
			UserDto objUserDto = userService.findByUsername(username);
			if (UserConstans.USER_DISABLE == objUserDto.getStatus()) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage(MessageUtil.MESSAGE_USER_LOCK);
				return responseDto;
			}
			// lay thong tin role
			RoleDto roleDto = roleService.findOne(Long.parseLong(String.valueOf(objUserSecurityDto.getRole())));
			if (UserConstans.USER_ROLE_DISABLE == roleDto.getStatus()) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage(MessageUtil.MESSAGE_USER_ROLE_LOCK);
				return responseDto;
			}
			responseDto.setCode(HttpStatus.OK.toString());
			UserInfo userInfo = new UserInfo();
			userInfo.setToken(TokenAuthenticationService.createTokenUser(String.valueOf(objUserDto.getId()),
					objUserSecurityDto.getGr(), objUserSecurityDto.getRole(),
					objUserSecurityDto.getRoleDelegatePayment() == 1 ? Boolean.TRUE : Boolean.FALSE));
			List<MenuDto> menus = userSecurityService.getMenuByRoleForApp(objUserSecurityDto.getRole());
			userInfo.setMenus(menus);
			responseDto.setData(userInfo);
			return responseDto;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			LOGGER.error("Lỗi hệ thống: " + e.getMessage());
			responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
			return responseDto;
		}
	}

}

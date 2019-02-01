package net.cis.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.cis.constants.ResponseErrorCodeConstants;
import net.cis.dto.ErrorDto;
import net.cis.dto.FuncDto;
import net.cis.dto.ResponseApi;
import net.cis.dto.RoleDto;
import net.cis.dto.RolePermissionDto;
import net.cis.service.FuncService;
import net.cis.service.RolePermissionService;
import net.cis.service.RoleService;

@RestController
@RequestMapping("/permission-new")
@Api("API liên quan tới việc mapping Role và Func")
public class RolePermissionEndpoint_New {
	protected final Logger LOGGER = Logger.getLogger(getClass());

	@Autowired
	RolePermissionService rolePermissionService;

	@Autowired
	FuncService funcService;

	@Autowired
	RoleService roleService;

	@RequestMapping(value = "/filter", method = RequestMethod.GET)
	@ApiOperation("get role permissions by conditions")
	@ResponseBody
	public ResponseApi filter(@RequestParam(value = "role") Long role) {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		try {
			RoleDto roleDto = new RoleDto();
			roleDto.setId(role);
			List<RolePermissionDto> dtos = rolePermissionService.findByRole(roleDto);

			responseApi.setError(errorDto);
			responseApi.setData(dtos);
			return responseApi;
		} catch (Exception ex) {
			LOGGER.error("Lỗi hệ thống: " + ex.getMessage());
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage(ex.getMessage());
			responseApi.setError(errorDto);
			return responseApi;
		}
	}

	@RequestMapping(value = "/assign", method = RequestMethod.POST)
	@ApiOperation("get role permissions by conditions")
	@ResponseBody
	public ResponseApi update(@RequestParam(value = "role") Long role, @RequestParam(value = "func") Long func,
			@RequestParam(value = "status") Boolean status) {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		try {
			RoleDto roleDto = roleService.findOne(role);
			if (roleDto == null) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("Role không tồn tại");
				responseApi.setError(errorDto);
				return responseApi;
			}

			FuncDto funcDto = funcService.findById(func);
			if (funcDto == null) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				responseApi.setError(errorDto);
				return responseApi;
			}

			RolePermissionDto dto = rolePermissionService.findOneByRoleAndFunc(roleDto, funcDto);
			RolePermissionDto newDto;

			// assign
			if (status) {
				if (dto == null) {
					dto = new RolePermissionDto();
					dto.setRole(roleDto);
					dto.setFunc(funcDto);
					dto.setStatus(1);
					newDto = rolePermissionService.create(dto);
				} else {
					newDto = dto;
				}
				responseApi.setData(newDto);
				responseApi.setError(errorDto);
				return responseApi;
			} else { // un assign
				if (dto != null) {
					rolePermissionService.delete(dto);
				}
				responseApi.setData(dto);
				responseApi.setError(errorDto);
				return responseApi;
			}
		} catch (Exception ex) {
			LOGGER.error("Lỗi hệ thống: " + ex.getMessage());
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage(ex.getMessage());
			responseApi.setError(errorDto);
			return responseApi;
		}
	}
}

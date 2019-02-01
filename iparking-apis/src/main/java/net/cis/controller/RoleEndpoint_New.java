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
import net.cis.dto.ResponseApi;
import net.cis.dto.RoleDto;
import net.cis.service.RoleService;

@RestController
@RequestMapping("/role-new")
@Api(value = "System roles", description = "URL to handle system roles")
public class RoleEndpoint_New {
	protected final Logger LOGGER = Logger.getLogger(getClass());

	@Autowired
	RoleService roleService;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ApiOperation("Lấy toàn bộ roles của hệ thống")
	public @ResponseBody ResponseApi getAll() {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		try {
			List<RoleDto> dtos = roleService.findAll();
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

	@RequestMapping(value = "/filter", method = RequestMethod.GET)
	@ApiOperation("Lấy toàn bộ roles của hệ thống")
	public @ResponseBody ResponseApi filter(@RequestParam(name = "status", required = false) Integer status) {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);

		try {
			if (status == null) {
				List<RoleDto> dtos = roleService.findAll();
				responseApi.setError(errorDto);
				responseApi.setData(dtos);
				return responseApi;
			} else {
				List<RoleDto> dtos = roleService.findByStatus(status);
				responseApi.setError(errorDto);
				responseApi.setData(dtos);
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

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ApiOperation("Thêm role của hệ thống")
	public @ResponseBody ResponseApi create(@RequestParam(name = "desc") String desc,
			@RequestParam(name = "status", required = false) Integer status) {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		try {
			desc = desc.trim();
			if (desc.isEmpty()) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("Mô tả cho Role không được để trống");
				responseApi.setError(errorDto);
				return responseApi;
			}
			RoleDto dto = new RoleDto();
			dto.setDesc(desc);
			if (status == null) {
				status = 1;
			}
			dto.setStatus(status);

			RoleDto newDto = roleService.create(dto);
			responseApi.setError(errorDto);
			responseApi.setData(newDto);
			return responseApi;
		} catch (Exception ex) {
			LOGGER.error("Lỗi hệ thống: " + ex.getMessage());
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage(ex.getMessage());
			responseApi.setError(errorDto);
			return responseApi;
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ApiOperation("Cập nhật role của hệ thống")
	public @ResponseBody ResponseApi update(@RequestParam(name = "id") Long id,
			@RequestParam(name = "desc", required = false) String desc,
			@RequestParam(name = "status", required = false) Integer status) {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);

		try {
			RoleDto dto = roleService.findOne(id);
			if (dto == null) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("Role không tồn tại");
				responseApi.setError(errorDto);
				return responseApi;
			}

			if (status != null) {
				dto.setStatus(status);
			}
			if (desc != null) {
				desc = desc.trim();
				if (desc.isEmpty()) {
					errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
					errorDto.setMessage("Mô tả cho role không được để trống");
					responseApi.setError(errorDto);
					return responseApi;
				}

				dto.setDesc(desc);
			}
			roleService.update(dto);
			responseApi.setError(errorDto);
			return responseApi;
		} catch (Exception ex) {
			LOGGER.error("Lỗi hệ thống: " + ex.getMessage());
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage(ex.getMessage());
			responseApi.setError(errorDto);
			return responseApi;
		}
	}
}

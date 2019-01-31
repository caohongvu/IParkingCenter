package net.cis.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
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
import net.cis.constants.UserConstans;
import net.cis.dto.ErrorDto;
import net.cis.dto.FuncDto;
import net.cis.dto.ResponseApi;
import net.cis.service.FuncService;

@RestController
@RequestMapping("/func")
@Api(value = "System functions", description = "URL to handle system functions (cpp, management...)")
public class FuncEndpoint {
	protected final Logger LOGGER = Logger.getLogger(getClass());

	@Autowired
	FuncService funcService;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ApiOperation("Danh sách chức năng được định nghĩa của hệ thống")
	public @ResponseBody ResponseApi getAll() {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		try {
			List<FuncDto> funcDtos = funcService.findAll();
			responseApi.setError(errorDto);
			responseApi.setData(funcDtos);
			return responseApi;
		} catch (Exception ex) {
			LOGGER.error("Lỗi hệ thống: " + ex.getMessage());
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage(ex.getMessage());
			responseApi.setError(errorDto);
			return responseApi;
		}
	}

	@RequestMapping(value = "/getAllFunctionParent", method = RequestMethod.GET)
	@ApiOperation("Danh sách chức năng được định nghĩa của hệ thống")
	public @ResponseBody ResponseApi getAllFunctionParent() {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		try {
			List<FuncDto> funcDtos = funcService.findAllFuncParent();
			responseApi.setError(errorDto);
			responseApi.setData(funcDtos);
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
	@ApiOperation("Danh sách chức năng hệ thống theo tiêu chí nào đó")
	public @ResponseBody ResponseApi filter(@RequestParam(value = "status", required = false) Integer status) {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		try {
			if (status == null) {
				List<FuncDto> funcDtos = funcService.findAll();
				responseApi.setError(errorDto);
				responseApi.setData(funcDtos);
				return responseApi;
			} else {
				List<FuncDto> funcDtos = funcService.findByStatus(status);
				responseApi.setError(errorDto);
				responseApi.setData(funcDtos);
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
	@ApiOperation("Tạo chức năng của hệ thống")
	public @ResponseBody ResponseApi create(@RequestParam(value = "name") String name,
			@RequestParam(value = "label") String label, @RequestParam(value = "desc") String desc,
			@RequestParam(value = "status") int status,
			@RequestParam(value = "parent_id", required = false) Long parentId,
			@RequestParam(name = "type") Integer type, @RequestParam(name = "is-menu") Integer isMenu) {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		try {
			if (StringUtils.isEmpty(name)) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("Tên chức năng không được để trống");
				responseApi.setError(errorDto);
				return responseApi;
			}
			name = name.trim();
			FuncDto temp = funcService.findOneByName(name);
			if (temp != null) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("Tên chức năng đã tồn tại");
				responseApi.setError(errorDto);
				return responseApi;
			}

			if (StringUtils.isEmpty(label)) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("Label của chức năng");
				responseApi.setError(errorDto);
				return responseApi;
			}
			label = label.trim();
			if (StringUtils.isEmpty(desc)) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("Mô tả chức năng không được để trống");
				responseApi.setError(errorDto);
				return responseApi;
			}
			desc = desc.trim();
			// kiem tra chuc nang cha
			FuncDto objFuncParent = null;
			if (parentId != null) {
				objFuncParent = funcService.findById(parentId);
				if (objFuncParent == null) {
					errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
					errorDto.setMessage("Chức năng cha không tồn tại");
					responseApi.setError(errorDto);
					return responseApi;
				}
			}
			FuncDto dto = new FuncDto();
			dto.setName(name);
			dto.setDescription(desc);
			dto.setLabel(label);
			dto.setStatus(status);
			if (objFuncParent != null) {
				dto.setParentId(objFuncParent.getId().intValue());
				dto.setLevel(UserConstans.FUNC_LEVEL_2);
			} else
				dto.setLevel(UserConstans.FUNC_LEVEL_1);
			dto.setType(type);
			dto.setIsMenu(isMenu);
			dto = funcService.create(dto);
			responseApi.setData(dto);
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

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ApiOperation("Cập nhật chức năng hệ thống")
	public @ResponseBody ResponseApi update(@RequestParam(value = "id") Long id,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "label", required = false) String label,
			@RequestParam(value = "desc", required = false) String desc,
			@RequestParam(value = "status", required = false) Integer status,
			@RequestParam(value = "parent_id", required = false) Long parentId,
			@RequestParam(name = "type", required = false) Integer type,
			@RequestParam(name = "is-menu") Integer isMenu) {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		try {
			if (id == null) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				responseApi.setError(errorDto);
				return responseApi;
			}
			FuncDto dto = funcService.findById(id);
			if (dto == null) {
				errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
				errorDto.setMessage("Chức năng không tồn tại.");
				responseApi.setError(errorDto);
				return responseApi;
			}
			if (!StringUtils.isEmpty(name)) {
				FuncDto tmp = funcService.findOneByNameAndIdNot(name, id);
				if (tmp != null) {
					errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
					errorDto.setMessage("Tên chức năng đã tồn tại.");
					responseApi.setError(errorDto);
					return responseApi;
				}
				dto.setName(name);
			}
			if (!StringUtils.isEmpty(name)) {
				dto.setDescription(desc);
			}
			if (status != null) {
				dto.setStatus(status);
			}
			// kiem tra chuc nang cha
			FuncDto objFuncParent = null;
			if (parentId != null) {
				objFuncParent = funcService.findById(parentId);
				if (objFuncParent == null) {
					errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
					errorDto.setMessage("Chức năng cha không tồn tại");
					responseApi.setError(errorDto);
					return responseApi;
				}
				dto.setParentId(objFuncParent.getId().intValue());
				dto.setLevel(UserConstans.FUNC_LEVEL_2);
			}
			dto.setType(type);
			dto.setIsMenu(isMenu);
			dto = funcService.update(dto);
			responseApi.setError(errorDto);
			responseApi.setData(dto);
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

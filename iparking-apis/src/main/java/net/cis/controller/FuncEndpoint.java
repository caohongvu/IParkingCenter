package net.cis.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.cis.dto.FuncDto;
import net.cis.dto.ResponseDto;
import net.cis.service.FuncService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/func")
@Api(value = "System functions", description = "URL to handle system functions (cpp, management...)")
public class FuncEndpoint {
	protected final Logger LOGGER = Logger.getLogger(getClass());

	@Autowired
	FuncService funcService;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ApiOperation("Danh sách chức năng được định nghĩa của hệ thống")
	public @ResponseBody ResponseDto getAll() {
		ResponseDto responseDto = new ResponseDto();
		try {
			List<FuncDto> funcDtos = funcService.findAll();
			responseDto.setCode(HttpStatus.OK.toString());
			responseDto.setData(funcDtos);
			return responseDto;
		} catch (Exception ex) {
			LOGGER.error("Lỗi hệ thống: " + ex.getMessage());
			responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
			return responseDto;
		}
	}

	@RequestMapping(value = "/getAllFunctionParent", method = RequestMethod.GET)
	@ApiOperation("Danh sách chức năng được định nghĩa của hệ thống")
	public @ResponseBody ResponseDto getAllFunctionParent() {
		ResponseDto responseDto = new ResponseDto();
		try {
			List<FuncDto> funcDtos = funcService.findAllFuncParent();
			responseDto.setCode(HttpStatus.OK.toString());
			responseDto.setData(funcDtos);
			return responseDto;
		} catch (Exception ex) {
			LOGGER.error("Lỗi hệ thống: " + ex.getMessage());
			responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
			return responseDto;
		}
	}

	@RequestMapping(value = "/filter", method = RequestMethod.GET)
	@ApiOperation("Danh sách chức năng hệ thống theo tiêu chí nào đó")
	public @ResponseBody ResponseDto filter(@RequestParam(value = "status", required = false) Integer status) {
		ResponseDto responseDto = new ResponseDto();
		try {
			if (status == null) {
				List<FuncDto> funcDtos = funcService.findAll();
				responseDto.setCode(HttpStatus.OK.toString());
				responseDto.setData(funcDtos);
				return responseDto;
			} else {
				List<FuncDto> funcDtos = funcService.findByStatus(status);
				responseDto.setCode(HttpStatus.OK.toString());
				responseDto.setData(funcDtos);
				return responseDto;
			}
		} catch (Exception ex) {
			LOGGER.error("Lỗi hệ thống: " + ex.getMessage());
			responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
			return responseDto;
		}
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ApiOperation("Tạo chức năng của hệ thống")
	public @ResponseBody ResponseDto create(@RequestParam(value = "name") String name,
			@RequestParam(value = "desc") String desc, @RequestParam(value = "status") Integer status) {
		ResponseDto responseDto = new ResponseDto();
		try {
			// Should check func name here?
			if (name == null) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage("Thiếu tên của chức năng");
				return responseDto;
			}
			name = name.trim();
			if (name.isEmpty()) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage("Tên chức năng không được để trống");
				return responseDto;
			}

			desc = desc.trim();
			if (desc.isEmpty()) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage("Mô tả chức năng không được để trống");
				return responseDto;
			}
			FuncDto temp = funcService.findOneByName(name);
			if (temp != null) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage("Tên chức năng đã tồn tại");
				return responseDto;
			}

			FuncDto dto = new FuncDto();
			dto.setName(name);
			dto.setDescription(desc);
			if (status == null) {
				status = 1;
			}
			dto.setStatus(status);
			FuncDto newDto = funcService.create(dto);
			responseDto.setCode(HttpStatus.OK.toString());
			responseDto.setData(newDto);
			return responseDto;
		} catch (Exception ex) {
			LOGGER.error("Lỗi hệ thống: " + ex.getMessage());
			responseDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
			responseDto.setMessage(ex.getMessage());
			return responseDto;
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ApiOperation("Cập nhật chức năng hệ thống")
	public @ResponseBody ResponseDto update(@RequestParam(value = "id") Long id,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "desc", required = false) String desc,
			@RequestParam(value = "status", required = false) Integer status) {
		ResponseDto responseDto = new ResponseDto();
		try {
			if (id == null) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				return responseDto;
			}
			FuncDto dto = funcService.findById(id);
			if (dto == null) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage("Chức năng không tồn tại.");
				return responseDto;
			}
			if (name != null) {
				name = name.trim();
				if (name.isEmpty()) {
					responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
					responseDto.setMessage("Tên chức năng không được trống.");
					return responseDto;
				}

				FuncDto tmp = funcService.findOneByNameAndIdNot(name, id);
				if (tmp != null) {
					responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
					responseDto.setMessage("Tên chức năng đã tồn tại.");
					return responseDto;
				}
			}
			if (name != null) {
				dto.setName(name);
			}
			if (desc != null) {
				dto.setDescription(desc);
			}
			if (status != null) {
				dto.setStatus(status);
			}
			funcService.update(dto);
			responseDto.setCode(HttpStatus.OK.toString());
			responseDto.setData(null);
			return responseDto;
		} catch (Exception ex) {
			LOGGER.error("Lỗi hệ thống: " + ex.getMessage());
			responseDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
			responseDto.setMessage(ex.getMessage());
			return responseDto;
		}
	}
}

package net.cis.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.cis.dto.ResponseDto;
import net.cis.dto.RoleDto;
import net.cis.service.RoleService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
@Api(value = "System roles", description = "URL to handle system roles")
public class RoleEndpoint {
    protected final Logger LOGGER = Logger.getLogger(getClass());

    @Autowired
    RoleService roleService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ApiOperation("Lấy toàn bộ roles của hệ thống")
    public @ResponseBody
    ResponseDto getAll() {
        ResponseDto responseDto = new ResponseDto();

        try {
            List<RoleDto> dtos = roleService.findAll();
            responseDto.setCode(HttpStatus.OK.toString());
            responseDto.setData(dtos);
            return responseDto;
        } catch(Exception ex){
            LOGGER.error("Lỗi hệ thống: " + ex.getMessage());
            responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
            return responseDto;
        }
    }

    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    @ApiOperation("Lấy toàn bộ roles của hệ thống")
    public @ResponseBody
    ResponseDto filter(@RequestParam(name = "status", required = false) Integer status) {
        ResponseDto responseDto = new ResponseDto();

        try {
            if (status == null) {
                List<RoleDto> dtos = roleService.findAll();
                responseDto.setCode(HttpStatus.OK.toString());
                responseDto.setData(dtos);
                return responseDto;
            } else {
                List<RoleDto> dtos = roleService.findByStatus(status);
                responseDto.setCode(HttpStatus.OK.toString());
                responseDto.setData(dtos);
                return responseDto;
            }
        } catch(Exception ex){
            LOGGER.error("Lỗi hệ thống: " + ex.getMessage());
            responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
            return responseDto;
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation("Thêm role của hệ thống")
    public @ResponseBody
    ResponseDto create(@RequestParam(name = "desc") String desc,
                       @RequestParam(name = "status", required = false) Integer status) {
        ResponseDto responseDto = new ResponseDto();

        try {
            desc = desc.trim();
            if (desc.isEmpty()) {
                responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
                responseDto.setMessage("Mô tả cho Role không được để trống");
                return responseDto;
            }
            RoleDto dto = new RoleDto();
            dto.setDesc(desc);
            if (status == null) {
                status = 1;
            }
            dto.setStatus(status);

            RoleDto newDto = roleService.create(dto);
            responseDto.setCode(HttpStatus.OK.toString());
            responseDto.setData(newDto);
            return responseDto;
        } catch(Exception ex){
            LOGGER.error("Lỗi hệ thống: " + ex.getMessage());
            responseDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            return responseDto;
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation("Cập nhật role của hệ thống")
    public @ResponseBody
    ResponseDto update(@RequestParam(name = "id") Long id,
                       @RequestParam(name = "desc", required = false) String desc,
                       @RequestParam(name = "status", required = false) Integer status) {
        ResponseDto responseDto = new ResponseDto();

        try {
            RoleDto dto = roleService.findOne(id);
            if (dto == null) {
                responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
                responseDto.setMessage("Role không tồn tại");
                return responseDto;
            }

            if (status != null) {
                dto.setStatus(status);
            }
            if (desc != null) {
                desc = desc.trim();
                if (desc.isEmpty()) {
                    responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
                    responseDto.setMessage("Mô tả cho role không được để trống");
                    return responseDto;
                }

                dto.setDesc(desc);
            }
            roleService.update(dto);

            responseDto.setCode(HttpStatus.OK.toString());
            return responseDto;
        } catch(Exception ex){
            LOGGER.error("Lỗi hệ thống: " + ex.getMessage());
            responseDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            return responseDto;
        }
    }
}

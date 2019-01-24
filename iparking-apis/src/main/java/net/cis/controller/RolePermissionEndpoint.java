package net.cis.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.cis.dto.FuncDto;
import net.cis.dto.ResponseDto;
import net.cis.dto.RoleDto;
import net.cis.dto.RolePermissionDto;
import net.cis.service.FuncService;
import net.cis.service.RolePermissionService;
import net.cis.service.RoleService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permission")
@Api("API liên quan tới việc mapping Role và Func")
public class RolePermissionEndpoint {
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
    public ResponseDto filter(@RequestParam(value = "role") Long role) {
        ResponseDto responseDto = new ResponseDto();
        try {
            RoleDto roleDto = new RoleDto();
            roleDto.setId(role);
            List<RolePermissionDto> dtos = rolePermissionService.findByRole(roleDto);

            responseDto.setCode(HttpStatus.OK.toString());
            responseDto.setData(dtos);
            return responseDto;
        } catch (Exception ex) {
            LOGGER.error("Lỗi hệ thống: " + ex.getMessage());
            responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
            return responseDto;
        }
    }

    @RequestMapping(value = "/assign", method = RequestMethod.POST)
    @ApiOperation("get role permissions by conditions")
    @ResponseBody
    public ResponseDto update(@RequestParam(value = "role") Long role,
                              @RequestParam(value = "func") Long func,
                              @RequestParam(value = "status") Boolean status) {
        ResponseDto responseDto = new ResponseDto();
        try {
            RoleDto roleDto = roleService.findOne(role);
            if (roleDto == null) {
                responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
                responseDto.setMessage("Role không tồn tại");
                return responseDto;
            }

            FuncDto funcDto = funcService.findById(func);
            if (funcDto == null) {
                responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
                return responseDto;
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

                responseDto.setCode(HttpStatus.OK.toString());
                responseDto.setData(newDto);
                return responseDto;
            } else { // un assign
                if (dto != null) {
                    rolePermissionService.delete(dto);
                }

                responseDto.setCode(HttpStatus.OK.toString());
                responseDto.setData(dto);
                return responseDto;
            }
        } catch (Exception ex) {
            LOGGER.error("Lỗi hệ thống: " + ex.getMessage());
            responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
            return responseDto;
        }
    }
}

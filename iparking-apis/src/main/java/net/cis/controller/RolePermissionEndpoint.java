package net.cis.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.cis.dto.ResponseDto;
import net.cis.dto.RolePermissionDto;
import net.cis.service.RolePermissionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/permission")
@Api("API liên quan tới việc mapping Role và Func")
public class RolePermissionEndpoint {
    protected final Logger LOGGER = Logger.getLogger(getClass());

    @Autowired
    RolePermissionService rolePermissionService;

    @RequestMapping(value = "/filter")
    @ApiOperation("get role permissions by conditions")
    @ResponseBody
    public ResponseDto filter(@RequestParam(value = "role") Long role) {
        ResponseDto responseDto = new ResponseDto();
        try {
            List<RolePermissionDto> dtos = rolePermissionService.findAll();

            responseDto.setCode(HttpStatus.OK.toString());
            responseDto.setData(dtos);
            return responseDto;
        } catch (Exception ex) {
            LOGGER.error("Lỗi hệ thống: " + ex.getMessage());
            responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
            return responseDto;
        }
    }
}

package net.cis.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.cis.dto.FuncDto;
import net.cis.dto.ResponseDto;
import net.cis.service.FuncService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/func")
@Api(value = "System functions", description = "URL to handle system functions (cpp, management...)")
public class FuncEndpoint {
    protected final Logger LOGGER = Logger.getLogger(getClass());

    @Autowired
    FuncService funcService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ApiOperation("Fetch all system functions")
    public @ResponseBody
    ResponseDto getAll() {
        ResponseDto responseDto  = new ResponseDto();
        try {
            List<FuncDto> funcDtos = funcService.findAll();

            responseDto.setData(funcDtos);
            return responseDto;
        } catch (Exception ex) {
            LOGGER.error("Lỗi hệ thống: " + ex.getMessage());
            responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
            return responseDto;
        }
    }
}

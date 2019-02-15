package net.cis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.cis.dto.ResponseDto;
import net.cis.dto.SmsConfigDto;
import net.cis.service.SmsConfigService;

@RestController
@RequestMapping("/sms-config")
@Api("APIs allows to enable/disable SMS payment")
public class SmsConfigEndpoint {
    @Autowired
    SmsConfigService smsConfigService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ApiOperation(value = "Lấy sms payment config hiện tại")
    public @ResponseBody ResponseDto get() {
        ResponseDto response = new ResponseDto();
        try {
            SmsConfigDto dto = smsConfigService.findFirstByOrderByIdAsc();
            response.setCode(HttpStatus.OK.toString());
            response.setData(dto);
            return response;
        }
        catch(Exception ex) {
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            response.setMessage("Xảy ra lỗi trong quá trình truy cập dữ liệu");
            return response;
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation("Update sms payment config")
    public @ResponseBody ResponseDto update(
            @RequestParam Long id,
            @RequestParam Boolean status
    ) {
        ResponseDto response = new ResponseDto();
        try {
            SmsConfigDto dto = new SmsConfigDto();
            dto.setId(id);
            dto.setStatus(status);

            smsConfigService.update(dto);
            response.setCode(HttpStatus.OK.toString());
            return response;
        }
        catch(Exception ex) {
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            response.setMessage("Xảy ra lỗi trong quá trình truy cập dữ liệu");
            return response;
        }
    }
}

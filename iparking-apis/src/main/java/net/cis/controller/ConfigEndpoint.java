package net.cis.controller;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.cis.dto.ResponseApi;
import net.cis.service.ConfigService;


@RestController
@RequestMapping("/config")
@Api(value = "config Endpoint", description = "The URL to handle config endpoint")
public class ConfigEndpoint {
	
	@Autowired
	private ConfigService configService;
	
	@RequestMapping(value = "/company", method = RequestMethod.GET)
	@ApiOperation("Get config of company")
	public @ResponseBody Object getConfigOfCompany(@RequestParam(name = "id", required = false) int id) throws Exception {
		ResponseApi enpoint = configService.findAllConfigOfCompany(id);
		return enpoint;
	}

}

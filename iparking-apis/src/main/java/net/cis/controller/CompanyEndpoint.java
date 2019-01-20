package net.cis.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.cis.constants.ResponseErrorCodeConstants;
import net.cis.dto.CompanyDto;
import net.cis.dto.ErrorDto;
import net.cis.dto.ResponseApi;
import net.cis.security.filter.TokenAuthenticationService;
import net.cis.service.CompanyService;

@RestController
@RequestMapping("/company")
@Api(value = "company Endpoint", description = "The URL to handle company endpoint")
public class CompanyEndpoint {
	protected final Logger LOGGER = Logger.getLogger(getClass());
	@Autowired
	CompanyService companyService;

	@RequestMapping(value = "/getCompanyDetail", method = RequestMethod.GET)
	@ApiOperation("Chi tiet cong ty")
	public @ResponseBody ResponseApi getCompany(HttpServletRequest request) throws Exception {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		try {
			errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
			responseApi.setError(errorDto);
			String companyId = TokenAuthenticationService.getAuthenticationInfo(request);
			CompanyDto objCompanyDto = companyService.findById(Long.parseLong(companyId));
			if (objCompanyDto == null) {
				return responseApi;
			}
			responseApi.setData(objCompanyDto);
			return responseApi;
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage(ex.getMessage());
			responseApi.setError(errorDto);
			return responseApi;
		}
	}

}

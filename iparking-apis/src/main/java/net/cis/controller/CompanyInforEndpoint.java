package net.cis.controller;

import java.util.List;

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
import net.cis.dto.ErrorDto;
import net.cis.dto.ResponseApi;
import net.cis.jpa.entity.CompanyInforEntity;
import net.cis.service.CompanyInforService;

@RestController
@RequestMapping("/company_infor")
@Api(value = "Company Infor Endpoint", description = "The URL to handle company endpoint")
public class CompanyInforEndpoint {
	
	protected final Logger LOGGER = Logger.getLogger(getClass());
	
	@Autowired
	CompanyInforService companyInforService;

	@RequestMapping(value = "/monthly_invoice", method = RequestMethod.GET)
	@ApiOperation("Monthly invoice")
	public @ResponseBody ResponseApi getCompanyNeedMonthlyInvoice(HttpServletRequest request) throws Exception {
		ResponseApi responseApi = new ResponseApi();
		try {
			List<CompanyInforEntity> companies = companyInforService.findCompanyNeedMonthlyInvoice();
			responseApi.setData(companies);
			responseApi.setError(new ErrorDto(ResponseErrorCodeConstants.StatusOK, ""));
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			responseApi.setData("");
			responseApi.setError(new ErrorDto(ResponseErrorCodeConstants.StatusBadRequest, ""));
		}
		
		return responseApi;
	}
	
}

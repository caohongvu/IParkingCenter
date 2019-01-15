package net.cis.controller;



import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.cis.constants.ResponseErrorCodeConstants;
import net.cis.dto.ErrorDto;
import net.cis.dto.ParkingActorDto;
import net.cis.dto.ParkingDto;
import net.cis.dto.ResponseApi;
import net.cis.security.filter.TokenAuthenticationService;
import net.cis.service.ConfigService;
import net.cis.service.ParkingActorService;
import net.cis.service.ParkingContractService;
import net.cis.service.ParkingService;


@RestController
@RequestMapping("/config")
@Api(value = "config Endpoint", description = "The URL to handle config endpoint")
public class ConfigEndpoint {
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	ParkingContractService parkingContractService;
	
	@Autowired
	ParkingActorService parkingActorService;
	
	@Autowired
	ParkingService parkingService;
	
	@RequestMapping(value = "/company", method = RequestMethod.GET)
	@ApiOperation("Get config of company")
	public @ResponseBody Object getConfigOfCompany(HttpServletRequest request) throws Exception {
		String cusId = TokenAuthenticationService.getAuthenticationInfo(request); 
		
		ErrorDto errorDto = new ErrorDto();
		ResponseApi responseApi = new ResponseApi();
		
		int actorId = Integer.parseInt(cusId);
		List<ParkingActorDto> parkingActorDtos = parkingActorService.findByActors(actorId);
		
		if (parkingActorDtos.size() < 1) {
			errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
			errorDto.setMessage("");
			responseApi.setError(errorDto);
			responseApi.setData(null);
			return responseApi;
		}
		// lay cappId
		long carpp_id = parkingActorDtos.get(0).getCppId();
		String cppId = new Long(carpp_id).toString();

		//lay ID company
		ParkingDto parkingDto = parkingService.findByOldId(cppId);
		int companyId = parkingDto.getCompany().intValue();  
		
		ResponseApi enpoint = configService.findAllConfigOfCompany(companyId);
		return enpoint;		
		
	}
	
	@RequestMapping(value = "/for_invoice", method = RequestMethod.GET) 
	public @ResponseBody ResponseApi getCompanyForInvoice(HttpServletRequest request) throws Exception {
		ResponseApi responseApi = configService.findCompanyNeedMonthlyInvoice();
		
		return responseApi;
	}

}

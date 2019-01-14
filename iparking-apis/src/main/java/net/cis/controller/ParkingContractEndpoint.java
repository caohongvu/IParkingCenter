package net.cis.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import net.cis.constants.ResponseErrorCodeConstants;
import net.cis.dto.ErrorDto;
import net.cis.dto.ParkingContractDto;
import net.cis.dto.ResponseApi;
import net.cis.jpa.criteria.ParkingContractCriteria;
import net.cis.security.filter.TokenAuthenticationService;
import net.cis.service.ParkingContractService;

@RestController
@RequestMapping("/parking-contract")
@Api(value = "parking Contract Endpoint", description = "The URL to handle parking contract endpoint")
public class ParkingContractEndpoint {
	protected final Logger LOGGER = Logger.getLogger(getClass());
	@Autowired
	ParkingContractService parkingContractService;

	@RequestMapping(value = "/{id}/get-by-id/", method = RequestMethod.GET)
	public @ResponseBody ParkingContractDto getByOldId(@PathVariable("id") Long id) throws Exception {
		ParkingContractDto parkingContractDto = parkingContractService.findOne(id);

		return parkingContractDto;
	}

	@RequestMapping(value = "/get-by-customer/", method = RequestMethod.GET)
	public @ResponseBody List<ParkingContractDto> getByCustomer(HttpServletRequest request) throws Exception {
		String cusId = TokenAuthenticationService.getAuthenticationInfo(request);
		List<ParkingContractDto> parkingContractDtos = parkingContractService.findByCustomer(Long.valueOf(cusId));

		return parkingContractDtos;
	}

	@RequestMapping(value = "/save/", method = RequestMethod.POST)
	public @ResponseBody ParkingContractDto created(@RequestBody ParkingContractDto parkingContractDto)
			throws Exception {

		return parkingContractService.save(parkingContractDto);
	}

	@RequestMapping(value = "/update/", method = RequestMethod.POST)
	public @ResponseBody ParkingContractDto update(@RequestParam(name = "id") long id,
			@RequestParam("updatedAt") String updatedAt, @RequestParam("paidAmount") Long paidAmount,
			@RequestParam("validTo") Long validTo) throws Exception {
		ParkingContractDto parkingContractDto = parkingContractService.findOne(id);
		parkingContractDto.setUpdatedAt(updatedAt);
		parkingContractDto.setValidTo(validTo);
		parkingContractDto.setPaidAmount(parkingContractDto.getPaidAmount() + paidAmount);
		return parkingContractService.update(parkingContractDto);
	}

	@RequestMapping(value = "/getParkingContractOutOfDate", method = RequestMethod.GET)
	public @ResponseBody ResponseApi getParkingContractOutOfDate(@RequestParam("parking_code") String parkingCode) {
		ResponseApi response = new ResponseApi();
		ErrorDto error = new ErrorDto();
		error.setCode(ResponseErrorCodeConstants.StatusOK);
		try {
			ParkingContractCriteria objParkingContractCriteria = new ParkingContractCriteria();
			objParkingContractCriteria.setCppCode(parkingCode);
			List<ParkingContractDto> lstParkingContractDto = parkingContractService
					.findParkingContractOutOfDate(objParkingContractCriteria);
			response.setData(lstParkingContractDto);
			response.setError(error);
			return response;
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			error.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			response.setError(error);
			return response;
		}

	}
}

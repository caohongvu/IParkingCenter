package net.cis.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.cis.common.util.StatusUtil;
import net.cis.constants.ResponseErrorCodeConstants;
import net.cis.dto.CompanyDto;
import net.cis.dto.ErrorDto;
import net.cis.dto.ParkingDto;
import net.cis.dto.ResponseApi;
import net.cis.dto.TicketDto;
import net.cis.jpa.criteria.TicketCriteria;
import net.cis.service.CompanyService;
import net.cis.service.ParkingService;
import net.cis.service.TicketService;

@RestController
@RequestMapping("/parking")
@Api(value = "parking Endpoint", description = "The URL to handle parking endpoint")
public class ParkingPlaceEndpoint {
	protected final Logger LOGGER = Logger.getLogger(getClass());
	@Autowired
	ParkingService parkingService;

	@Autowired
	CompanyService companyService;

	@Autowired
	TicketService ticketService;

	@RequestMapping(value = "/{id}/getByOldId", method = RequestMethod.GET)
	public @ResponseBody ParkingDto getByOldId(@PathVariable("id") String oldId) throws Exception {
		ParkingDto parkingDto = parkingService.findByOldId(oldId);
		TicketCriteria ticketCriteria = new TicketCriteria();
		ticketCriteria.setCppId(parkingDto.getId());
		ticketCriteria.setInSession(1);

		Pageable pageable = new PageRequest(0, 1000);
		List<TicketDto> ticketsInsession = ticketService.findAll(ticketCriteria, pageable);
		parkingDto.setCurrentTicketInSession(ticketsInsession.size());
		return parkingDto;
	}

	@RequestMapping(value = "/update_cap_adjust", method = RequestMethod.POST)
	public @ResponseBody ParkingDto manualAdjust(HttpServletRequest request, @RequestParam("cppId") long cppId,
			@RequestParam("adjust") int adjust, @RequestParam("current_ticket_count") int currentTicketCount)
			throws Exception {
		ParkingDto parkingDto = parkingService.findById(cppId);
		TicketCriteria ticketCriteria = new TicketCriteria();
		ticketCriteria.setCppId(parkingDto.getId());
		ticketCriteria.setInSession(StatusUtil.SUCCESS_STATUS);

		Pageable pageable = new PageRequest(0, 1000);
		List<TicketDto> ticketsInsession = ticketService.findAll(ticketCriteria, pageable);
		int currentTicketInssessionFromDatabase = ticketsInsession.size();
		int newAdjust = adjust;
		if (currentTicketInssessionFromDatabase != currentTicketCount) {
			newAdjust = adjust - (currentTicketCount - currentTicketInssessionFromDatabase);
		}
		parkingDto.setAdjust(newAdjust);
		parkingDto.setCurrentTicketInSession(currentTicketInssessionFromDatabase);
		if (parkingDto.getRemain() <= 0) {
			newAdjust = currentTicketInssessionFromDatabase - parkingDto.getCapacity();
		} else if (parkingDto.getRemain() >= parkingDto.getCapacity()) {
			newAdjust = currentTicketInssessionFromDatabase;
		}
		parkingDto.setAdjust(newAdjust);
		parkingDto = parkingService.save(parkingDto);

		return parkingDto;
	}

	/**
	 * liemnh
	 * 
	 * @param request
	 * @param company
	 * @return
	 */
	@RequestMapping(value = "/getParkingPlace", method = RequestMethod.GET)
	@ApiOperation("Danh sach diem do theo cong ty")
	public @ResponseBody ResponseApi getParkingPlace(HttpServletRequest request,
			@RequestParam("company") String company) {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		responseApi.setError(errorDto);
		try {
			CompanyDto objCompanyDto = companyService.findByCompanyCode(company);
			if (objCompanyDto == null) {
				return responseApi;
			}
			List<ParkingDto> lstParkingDto = parkingService.findByCompany(objCompanyDto.getId());
			responseApi.setData(lstParkingDto);
			return responseApi;
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			responseApi.setError(errorDto);
			return responseApi;
		}

	}
	
	@RequestMapping(value = "/by_company_id", method = RequestMethod.GET)
	@ApiOperation("Get PP by Company Id")
	public ResponseApi getParkingPlace(HttpServletRequest request, @RequestParam("id") Long id) {
		ResponseApi responseApi = new ResponseApi();
		try {
			List<ParkingDto> lstParkingDto = parkingService.findByCompany(id);
			responseApi.setData(lstParkingDto);
			responseApi.setError(new ErrorDto(ResponseErrorCodeConstants.StatusOK, ""));
			return responseApi;
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			responseApi.setData(null);
			responseApi.setError(new ErrorDto(ResponseErrorCodeConstants.StatusBadRequest, ""));
			return responseApi;
		}

	}
}

package net.cis.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import net.cis.common.util.StatusUtil;
import net.cis.dto.ParkingDto;
import net.cis.dto.TicketDto;
import net.cis.jpa.criteria.TicketCriteria;
import net.cis.service.ParkingService;
import net.cis.service.TicketService;

@RestController
@RequestMapping("/parking")
@Api(value="parking Endpoint", description="The URL to handle parking endpoint")
public class ParkingPlaceEndpoint {
	
	@Autowired
	ParkingService parkingService;
	
	@Autowired
	TicketService ticketService;
	
	@RequestMapping(value="/{id}/getByOldId", method= RequestMethod.GET)
	public @ResponseBody ParkingDto getByOldId(@PathVariable("id") String oldId) throws Exception{
		ParkingDto parkingDto = parkingService.findByOldId(oldId);
		TicketCriteria ticketCriteria = new TicketCriteria();
		ticketCriteria.setCppId(parkingDto.getId());
		ticketCriteria.setInSession(1);
		
		Pageable pageable = new PageRequest(0, 1000);
		List<TicketDto> ticketsInsession = ticketService.findAll(ticketCriteria, pageable);
		parkingDto.setCurrentTicketInSession(ticketsInsession.size());
		return parkingDto;
	}
	
	@RequestMapping(value="/update_cap_adjust", method= RequestMethod.POST)
	public @ResponseBody ParkingDto manualAdjust(HttpServletRequest request, 
			@RequestParam("cppId") long cppId,
			@RequestParam("adjust")int adjust,
			@RequestParam("current_ticket_count") int currentTicketCount) throws Exception{
		ParkingDto parkingDto = parkingService.findById(cppId);
		TicketCriteria ticketCriteria = new TicketCriteria();
		ticketCriteria.setCppId(parkingDto.getId());
		ticketCriteria.setInSession(StatusUtil.SUCCESS_STATUS);
		
		Pageable pageable = new PageRequest(0, 1000);
		List<TicketDto> ticketsInsession = ticketService.findAll(ticketCriteria, pageable);
		int currentTicketInssessionFromDatabase = ticketsInsession.size();
		int newAdjust = adjust;
		if(currentTicketInssessionFromDatabase != currentTicketCount) {
			newAdjust = adjust - (currentTicketCount - currentTicketInssessionFromDatabase);
		}
		parkingDto.setAdjust(newAdjust);
		parkingDto.setCurrentTicketInSession(currentTicketInssessionFromDatabase);
		if(parkingDto.getRemain() <= 0) {
			newAdjust = currentTicketInssessionFromDatabase - parkingDto.getCapacity();
		} else if(parkingDto.getRemain() >= parkingDto.getCapacity()) {
			newAdjust = currentTicketInssessionFromDatabase;
		}
		parkingDto.setAdjust(newAdjust);
		parkingDto = parkingService.save(parkingDto);
		
		return parkingDto;
	}
}

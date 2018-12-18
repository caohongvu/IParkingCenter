package net.cis.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.cis.common.web.BaseEndpoint;
import net.cis.dto.TicketDto;
import net.cis.jpa.criteria.TicketCriteria;
import net.cis.service.TicketService;

/**
 * Created by Vincent on 02/10/2018
 */
@RestController
@RequestMapping("/ticket")
@Api(value = "ticket Endpoint", description = "The URL to handle ticket endpoint")
public class TicketEndpoint extends BaseEndpoint {

	@Autowired
	TicketService ticketService;
	
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyyy HH:mm:ss");
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ApiOperation("Fetch all ticket")
	public @ResponseBody List<TicketDto> fetchTickets(
			@RequestParam(name="cpp_id", required=false) Long cppId,
			@RequestParam(name="in_session", required=false) Integer inSession,
			@RequestParam(name="date", required=false) String date,
			@RequestParam(name="page", required=false, defaultValue="1") int page,
			@RequestParam(name="size", required=false, defaultValue="500") int size) throws Exception {
		
		TicketCriteria ticketCriteria = new TicketCriteria();
		
		Calendar calendar = Calendar.getInstance();
		Date toDate = calendar.getTime();
		if(date != null) {
			toDate = format.parse(date +" 23:59:59");
			calendar.setTime(format.parse(date+" 00:00:00"));
		}
		calendar.set(Calendar.HOUR, -36);
		Date fromDate = calendar.getTime();
		
		
		page = page -1;
		if(page < 0) {
			page = 0;
		}
		
		if(cppId != null) {
			ticketCriteria.setCppId(cppId);
		}
		if(inSession != null) {
			ticketCriteria.setInSession(inSession);
		}
		ticketCriteria.setFromDate(fromDate);
		ticketCriteria.setToDate(toDate);
		
		Pageable pageable = new PageRequest(page, size);
		List<TicketDto> tickets = ticketService.findAll(ticketCriteria, pageable);
		
		return tickets;
	}
	
	@RequestMapping(value = "/{id}/find-by-id/", method = RequestMethod.GET)
	@ApiOperation("Fetch details of ticket")
	public @ResponseBody TicketDto getById(@PathVariable("id") Long id) throws Exception {
		TicketDto ticket = ticketService.findById(id);
		return ticket;
	}
	
	@RequestMapping(value = "/update/", method = RequestMethod.POST)
	@ApiOperation("Update details of ticket")
	public @ResponseBody TicketDto update(@RequestBody TicketDto ticketDto) throws Exception {
		TicketDto ticket = ticketService.save(ticketDto);
		return ticket;
	}
	
	@RequestMapping(value = "/create/", method = RequestMethod.POST)
	@ApiOperation("Create details of ticket")
	public @ResponseBody TicketDto create(@RequestBody TicketDto ticketDto) throws Exception {
		TicketDto ticket = ticketService.save(ticketDto);
		return ticket;
	}
	
	
}

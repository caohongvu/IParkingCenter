package net.cis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.cis.common.web.BaseEndpoint;
import net.cis.dto.ResponseApi;
import net.cis.dto.TicketTransactionDto;
import net.cis.dto.UpdateInvoiceDto;
import net.cis.service.TicketTransactionService;

/**
 * Created by Vincent on 02/10/2018
 */
@RestController
@RequestMapping("/ticket-transaction")
@Api(value = "ticket Endpoint", description = "The URL to handle ticket endpoint")
public class TicketTransactionEndpoint extends BaseEndpoint {

	@Autowired
	TicketTransactionService ticketTransactionService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ApiOperation("Fetch all ticket transactions")
	public @ResponseBody List<TicketTransactionDto> fetchTicketTransaction() throws Exception {
		List<TicketTransactionDto> ticketTransactions = ticketTransactionService.findAll();
		return ticketTransactions;
	}
	
	@RequestMapping(value = "/{id}/find-by-id/", method = RequestMethod.GET)
	@ApiOperation("Fetch details of transaction")
	public @ResponseBody TicketTransactionDto getById(@PathVariable("id") String id) throws Exception {
		TicketTransactionDto ticketTransaction = ticketTransactionService.findById(id);
		return ticketTransaction;
	}
	
	@RequestMapping(value = "/{paymentOrderNo}/find-payment-order-no/", method = RequestMethod.GET)
	@ApiOperation("Fetch details of transaction")
	public @ResponseBody TicketTransactionDto getByPaymentOrder(@PathVariable("paymentOrderNo") String paymentOrderNo) throws Exception {
		TicketTransactionDto ticketTransaction = ticketTransactionService.findByPaymentOrderNo(paymentOrderNo);
		return ticketTransaction;
	}
	
	
	@RequestMapping(value = "/update/", method = RequestMethod.POST)
	@ApiOperation("Update details of transaction")
	public @ResponseBody TicketTransactionDto updateTransaction(@RequestBody TicketTransactionDto ticketTransactionDto) throws Exception {
		TicketTransactionDto ticketTransaction = ticketTransactionService.save(ticketTransactionDto);
		return ticketTransaction;
	}
	
	@RequestMapping(value = "/create/", method = RequestMethod.POST)
	@ApiOperation("Create details of transaction")
	public @ResponseBody TicketTransactionDto createTransaction(@RequestBody TicketTransactionDto ticketTransactionDto) throws Exception {
		TicketTransactionDto ticketTransaction = ticketTransactionService.save(ticketTransactionDto);
		return ticketTransaction;
	}
	
	@RequestMapping(value = "/{id}/delete/", method = RequestMethod.POST)
	@ApiOperation("Create details of transaction")
	public void deleteTransaction(@RequestBody TicketTransactionDto ticketTransactionDto) throws Exception {
		ticketTransactionService.delete(ticketTransactionDto);
	}
	
	@RequestMapping(value = "/{ticket_id}/transactions/", method = RequestMethod.GET)
	@ApiOperation("Select all ticket by Ticket_id")
	public @ResponseBody Object fetchTicketTransactionByTicketId(@PathVariable("ticket_id") Long ticketId) throws Exception {
			ResponseApi enpoint = ticketTransactionService.findByTicketId(ticketId);
			return enpoint;
	}
	
	@RequestMapping(value = "/{id}/portal", method = RequestMethod.GET)
	@ApiOperation("Detail for transaction in portal")
	public @ResponseBody Object getTicketTransactionDetailPortal(@PathVariable("id") String id) throws Exception {
			ResponseApi enpoint = ticketTransactionService.getDetailPortal(id);
			return enpoint;
	}
	
	@RequestMapping(value = "/update_invoice", method = RequestMethod.POST)
	@ApiOperation("Update invoice code for ticket transaction")
	public @ResponseBody Object updateInvoiceForTran(@RequestBody UpdateInvoiceDto dto) throws Exception {
		ResponseApi enpoint = ticketTransactionService.updateInvoice(dto);
		return enpoint;
	}
	
	
	
}

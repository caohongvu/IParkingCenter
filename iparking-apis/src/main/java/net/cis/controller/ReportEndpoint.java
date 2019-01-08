package net.cis.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.cis.common.util.StatusUtil;
import net.cis.dto.ErrorDto;
import net.cis.dto.ResponseApi;
import net.cis.jpa.criteria.DailyTicketPaymentCriteria;
import net.cis.jpa.criteria.MonthlyTicketPaymentCriteria;
import net.cis.security.filter.TokenAuthenticationService;
import net.cis.service.DailyTicketPaymentService;
import net.cis.service.MonthlyTicketPaymentService;
import net.cis.service.ReportDelegatePaymentService;


@RestController
@RequestMapping("/report")
@Api(value = "report Endpoint", description = "The URL to handle report endpoint")
public class ReportEndpoint {
	
	@Autowired
	private DailyTicketPaymentService dailyTicketPaymentService;
	
	@Autowired
	private MonthlyTicketPaymentService monthlyTicketPaymentService;
	
	@Autowired
	private ReportDelegatePaymentService reportDelegatePaymentService;
	
	@RequestMapping(value = "/daily/ticket/payment", method = RequestMethod.GET)
	@ApiOperation("Fetch all ticket payment")
	public @ResponseBody Object fetchTicketsPayment(
			@RequestParam(name = "orderID", required = false) String orderID,
			@RequestParam(name = "transID", required = false) String transID,
			@RequestParam(name = "cpp_code", required = false) String cppCode,
			@RequestParam(name = "number_plate", required = false) String numberPlate,
			@RequestParam(name = "phone", required = false) Long phone,
			@RequestParam(name = "from_time", required = false) Long start_time,
			@RequestParam(name = "end_time", required = false) Long end_time,
			@RequestParam(name = "card_number", required = false) String cardNumber,
			@RequestParam(name = "trans_type", required = false) String transType,
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@RequestParam(name = "size", required = false, defaultValue = "500") int size) throws Exception {

		DailyTicketPaymentCriteria ticketCriteria = new DailyTicketPaymentCriteria();
		SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date toDate = new Date(end_time*1000L);

		Date fromDate = new Date(start_time*1000L);

		page = page - 1;
		if (page < 0) {
			page = 0;
		}

		if (cppCode != null && cppCode != "") {
			ticketCriteria.setCppCode(cppCode.toUpperCase());
		}

		if (orderID != null && orderID != "") {
			ticketCriteria.setOrderID(orderID);
		}

		if (transID != null && transID != "") {
			ticketCriteria.setTransId(transID);
		}

		if (phone != null && phone != 0) {
			ticketCriteria.setPhone(phone);
		}

		if (numberPlate != null && numberPlate != "") {
			ticketCriteria.setNumberplate(numberPlate.toUpperCase());
		}

		if (cardNumber != null && cardNumber != "") {
			ticketCriteria.setCardNumber(cardNumber);
		}

		if (transType != null && transType != "") {
			ticketCriteria.setTransType(transType);
		}

		ticketCriteria.setStart_time(formatTime.format(fromDate));
		ticketCriteria.setEnd_time(formatTime.format(toDate));

		Pageable pageable = new PageRequest(page, size);
		ResponseApi enpoint = dailyTicketPaymentService.findAllFooter(ticketCriteria, pageable);

		return enpoint;
	}
	
	@RequestMapping(value = "/monthly/ticket/payment", method = RequestMethod.GET)
	@ApiOperation("Fetch all monthly ticket payment")
	public @ResponseBody Object fetchMonthlyTicketsPayment(
			@RequestParam(name = "transID", required = false) String transID,
			@RequestParam(name = "cpp_code", required = false) String cppCode,
			@RequestParam(name = "number_plate", required = false) String numberPlate,
			@RequestParam(name = "contract_no", required = false) String contract_no,
			@RequestParam(name = "contract_code", required = false) String contract_code,
			@RequestParam(name = "phone", required = false) Long phone,
			@RequestParam(name = "from_time", required = false) Long start_time,
			@RequestParam(name = "end_time", required = false) Long end_time,
			@RequestParam(name = "card_number", required = false) String cardNumber,
			@RequestParam(name = "period_payment", required = false) String period_payment,
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@RequestParam(name = "size", required = false, defaultValue = "500") int size) throws Exception {

		MonthlyTicketPaymentCriteria ticketCriteria = new MonthlyTicketPaymentCriteria();
		SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date toDate = new Date(end_time*1000L);

		Date fromDate = new Date(start_time*1000L);

		page = page - 1;
		if (page < 0) {
			page = 0;
		}

		if (cppCode != null && cppCode != "") {
			ticketCriteria.setCppCode(cppCode.toUpperCase());
		}


		if (transID != null && transID != "") {
			ticketCriteria.setTransId(transID);
		}

		if (phone != null && phone != 0) {
			ticketCriteria.setPhone(phone);
		}

		if (numberPlate != null && numberPlate != "") {
			ticketCriteria.setNumberplate(numberPlate.toUpperCase());
		}

		if (cardNumber != null && cardNumber != "") {
			ticketCriteria.setCardNumber(cardNumber);
		}
		
		if (contract_no != null && contract_no != "") {
			ticketCriteria.setContract_no(contract_no);;
		}
		
		if (contract_code != null && contract_code != "") {
			ticketCriteria.setContract_code(contract_code);
		}
		
		if (period_payment != null && period_payment != "") {
			ticketCriteria.setPeriodPayment(period_payment);;
		}

		ticketCriteria.setStart_time(formatTime.format(fromDate));
		ticketCriteria.setEnd_time(formatTime.format(toDate));

		Pageable pageable = new PageRequest(page, size);
		ResponseApi enpoint = monthlyTicketPaymentService.findAll(ticketCriteria, pageable);

		return enpoint;
	}

	
	@RequestMapping(value = "/supervisor/delegate/payment", method = RequestMethod.GET)
	@ApiOperation("Fetch all delegate ticket payment")
	public @ResponseBody ResponseApi fetchDelegateTicketsPayment(HttpServletRequest request, @RequestParam("date") String date) {
		ResponseApi response = new ResponseApi();
		
		Long[] carppIds = null;
		String supervisorId = TokenAuthenticationService.getAuthenticationInfo(request);
		
		
		
		ErrorDto error = new ErrorDto();
		error.setCode(StatusUtil.SUCCESS_STATUS);
		error.setMessage(StatusUtil.SUCCESS_MESSAGE);
		response.setData(reportDelegatePaymentService.findByCarppIdsAndDate(carppIds,date));
		response.setError(error);
		
		return response;
	}
	
}

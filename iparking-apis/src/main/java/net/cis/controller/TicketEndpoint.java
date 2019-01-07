
package net.cis.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.cis.common.util.DateTimeUtil;
import net.cis.common.util.TicketUtil;
import net.cis.common.util.Utils;
import net.cis.common.util.constant.TicketConstants;
import net.cis.common.web.BaseEndpoint;
import net.cis.dto.CustomerDto;
import net.cis.dto.MonthlyTicketDto;
import net.cis.dto.ParkingDto;
import net.cis.dto.ResponseDto;
import net.cis.dto.TicketDto;
import net.cis.jpa.criteria.MonthlyTicketCriteria;
import net.cis.jpa.criteria.TicketCriteria;
import net.cis.security.filter.TokenAuthenticationService;
import net.cis.service.CustomerService;
import net.cis.service.EmailService;
import net.cis.service.MonthlyTicketService;
import net.cis.service.TicketService;
import net.cis.service.cache.MonthlyTicketCache;
import net.cis.service.cache.ParkingPlaceCache;

/**
 * Created by Vincent on 02/10/2018
 */
@RestController
@RequestMapping("/ticket")
@Api(value = "ticket Endpoint", description = "The URL to handle ticket endpoint")
public class TicketEndpoint extends BaseEndpoint {
	protected final Logger LOGGER = Logger.getLogger(getClass());
	@Autowired
	CustomerService customerService;
	@Autowired
	TicketService ticketService;

	@Autowired
	MonthlyTicketService monthlyTicketService;

	@Autowired
	private ParkingPlaceCache parkingPlaceCache;

	@Autowired
	private MonthlyTicketCache monthlyTicketCache;

	@Autowired
	EmailService emailService;

	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	SimpleDateFormat shortFormat = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ApiOperation("Fetch all ticket")
	public @ResponseBody List<TicketDto> fetchTickets(@RequestParam(name = "cpp_id", required = false) Long cppId,
			@RequestParam(name = "in_session", required = false) Integer inSession,
			@RequestParam(name = "date", required = false) String date,
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@RequestParam(name = "size", required = false, defaultValue = "500") int size) throws Exception {

		TicketCriteria ticketCriteria = new TicketCriteria();

		Calendar calendar = Calendar.getInstance();
		if (date == null || date.trim().isEmpty()) {
			date = shortFormat.format(calendar.getTime());
		}

		Date toDate = format.parse(date + " 23:59:59");
		calendar.setTime(format.parse(date + " 00:00:00"));
		calendar.set(Calendar.HOUR, -36);

		Date fromDate = calendar.getTime();

		if (inSession == null || (inSession != null && inSession == 0)) {
			if (Calendar.getInstance().after(toDate)) {
				fromDate = format.parse(date + " 00:00:00");
			} else {
				if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) > 7) {
					fromDate = format.parse(date + " 00:00:00");
				} else {
					calendar.setTime(format.parse(date + " 18:00:00"));
					calendar.set(Calendar.DATE, -1);
					fromDate = calendar.getTime();
				}
			}
		}

		page = page - 1;
		if (page < 0) {
			page = 0;
		}

		if (cppId != null) {
			ticketCriteria.setCppId(cppId);
		}
		if (inSession != null) {
			ticketCriteria.setInSession(inSession);
		}
		ticketCriteria.setFromDate(fromDate);
		ticketCriteria.setToDate(toDate);
		ticketCriteria.setStatus(TicketConstants.PAID_TICKET);

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

	@RequestMapping(value = "/find-by-customer", method = RequestMethod.GET)
	@ApiOperation("Fetch all ticket")
	public @ResponseBody List<TicketDto> fetchByCustomer(HttpServletRequest request,
			@RequestParam(name = "fromDate", required = false) Long from,
			@RequestParam(name = "toDate", required = false) Long to,
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@RequestParam(name = "size", required = false, defaultValue = "500") int size) throws Exception {

		page = page - 1;
		if (page < 0) {
			page = 0;
		}

		TicketCriteria ticketCriteria = new TicketCriteria();
		long customer = Long.parseLong(TokenAuthenticationService.getAuthenticationInfo(request));
		Pageable pageable = new PageRequest(page, size);

		Date fromDate = new Date(from);

		if (to == null) {
			to = Calendar.getInstance().getTimeInMillis();
		}
		Date toDate = new Date(to);

		ticketCriteria.setFromDate(fromDate);
		ticketCriteria.setToDate(toDate);
		ticketCriteria.setCustomer(customer);
		ticketCriteria.setStatus(1);
		List<TicketDto> tickets = ticketService.findAll(ticketCriteria, pageable);

		return tickets;
	}

	@RequestMapping(value = "/monthly/{id}/detail", method = RequestMethod.GET)
	@ApiOperation("Get detail monthly ticket by ticket id")
	public @ResponseBody MonthlyTicketDto fecthDetailMonthlyTicket(HttpServletRequest request,
			@RequestParam("ticketId") long ticketId) throws Exception {

		MonthlyTicketDto ticket = monthlyTicketService.findOne(ticketId);

		return ticket;
	}

	@RequestMapping(value = "/monthly/", method = RequestMethod.POST)
	@ApiOperation("Get all valid monthly ticket belong parking place")
	public @ResponseBody List<MonthlyTicketDto> fecthMonthlyTicket(HttpServletRequest request,
			@RequestParam("cppCode") String cppCode,
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@RequestParam(name = "size", required = false, defaultValue = "500") int size) throws Exception {

		page = page - 1;
		if (page < 0) {
			page = 0;
		}
		Pageable pageable = new PageRequest(page, size);

		MonthlyTicketCriteria ticketCriteria = new MonthlyTicketCriteria();
		ticketCriteria.setCppCode(cppCode);
		List<MonthlyTicketDto> tickets = monthlyTicketService.findAll(ticketCriteria, pageable);

		return tickets;
	}

	@RequestMapping(value = "/session-out/", method = RequestMethod.POST)
	@ApiOperation("Update ticket for session out")
	public @ResponseBody TicketDto updateTicketSessionOut(@RequestParam("id") Long id) throws Exception {
		TicketDto ticket = ticketService.findById(id);
		ticket.setInSession(false);
		ticket.setActualEndTime(Utils.getDatetimeFormatVN(Calendar.getInstance().getTime(), "yyyy-MM-dd HH:mm:ss"));
		ticket = ticketService.save(ticket);
		monthlyTicketCache.remove(ticket.getMonthlyTicketId());

		return ticket;
	}

	@RequestMapping(value = "/session-in/", method = RequestMethod.POST)
	@ApiOperation("Update ticket for session out")
	public @ResponseBody MonthlyTicketDto updateTicketSessionIn(@RequestParam("monthlyTicketId") Long monthlyTicketId)
			throws Exception {

		MonthlyTicketDto monthlyTicketDto = monthlyTicketService.findOne(monthlyTicketId);
		ParkingDto parkingDto = parkingPlaceCache.get(monthlyTicketDto.getParkingPlace());

		TicketDto ticket = new TicketDto();
		ticket.setCarNumberPlate(monthlyTicketDto.getNumberPlate());
		ticket.setCarPricingGroup(TicketConstants.MONTHLY_PRICE_GROUP);
		ticket.setCarType(TicketConstants.MONTHLY_CAR_TYPE);
		ticket.setCreatedAt(Calendar.getInstance().getTime());
		ticket.setCustomer(TicketConstants.MONTHLY_CUSTOMER);
		ticket.setParkingPlace(Long.valueOf(parkingDto.getOldId()));
		ticket.setStartTime(Utils.getDatetimeFormatVN(Calendar.getInstance().getTime(), "yyyy-MM-dd HH:mm:ss"));
		ticket.setStatus(TicketConstants.PAID_TICKET);
		ticket.setTicketData(TicketConstants.MONTHLY_TICKE_DATA);
		ticket.setUpdatedAt(Utils.getDatetimeFormatVN(Calendar.getInstance().getTime(), "yyyy-MM-dd HH:mm:ss"));
		ticket.setInSession(true);
		ticket.setMonthlyTicketId(monthlyTicketDto.getId());
		ticket.setId(TicketUtil.generateTicketId());
		ticket = ticketService.save(ticket);

		monthlyTicketCache.put(monthlyTicketDto.getId(), ticket.getId());
		monthlyTicketDto.setInSession(true);
		monthlyTicketDto.setInSessionTicketId(ticket.getId());
		return monthlyTicketDto;
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

	@RequestMapping(value = "/create-ticket", method = RequestMethod.POST)
	@ApiOperation("Create ticket from app suppervisor")
	public @ResponseBody ResponseDto createTicketForAppSupperVisor(@RequestParam("customerPhone") String customerPhone,
			@RequestParam("parkingPlace") Long parkingPlace, @RequestParam("carType") int carType,
			@RequestParam("carPricingGroup") int carPricingGroup, @RequestParam("carNumberPlate") String carNumberPlate,
			@RequestParam(name = "email", required = false) String email,
			@RequestParam(name = "otp", required = false) String otp) throws Exception {
		ResponseDto responseDto = new ResponseDto();
		responseDto.setCode(HttpStatus.OK.toString());
		LOGGER.info("create-ticket customerPhone:" + customerPhone);
		LOGGER.info("create-ticket parkingPlace:" + parkingPlace);
		LOGGER.info("create-ticket carType:" + carType);
		LOGGER.info("create-ticket carPricingGroup:" + carPricingGroup);
		LOGGER.info("create-ticket carNumberPlate:" + carNumberPlate);
		LOGGER.info("create-ticket email:" + email);
		try {
			if (StringUtils.isEmpty(customerPhone)) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage("Số điện thoại khách hàng sai định dạng");
				return responseDto;
			}

			if (!Utils.validateVNPhoneNumber(customerPhone)) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage("Số điện thoại khách hàng sai định dạng");
				return responseDto;
			}

			if (!Utils.validateNumberPlate(carNumberPlate)) {
				responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
				responseDto.setMessage("Biển số xe sai định dạng");
				return responseDto;
			}
			TicketDto objTicketDto = new TicketDto();
			// thuc hien generate ID ticket
			objTicketDto.setId(TicketUtil.generateTicketId());
			// lay thong tin customer
			CustomerDto objCustomerDto = customerService.findByPhone2(customerPhone);
			long customerId = 0l;
			if (objCustomerDto != null) {
				customerId = objCustomerDto.getOldId();
			} else {
				// thuc hien tao customer bên db shard
				Map<String, Object> resultCreateCustomer = customerService.createCustomerInPoseidonDb(customerPhone);
				if (resultCreateCustomer == null
						|| !HttpStatus.OK.toString().equals(resultCreateCustomer.get("Code"))) {
					responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
					responseDto.setMessage("Lỗi tạo Customer");
					return responseDto;
				}
				customerId = (long) resultCreateCustomer.get("Data");
				// thuc hien tao customer ben db iparking_center
				CustomerDto objCustomerDtoSave = new CustomerDto();
				objCustomerDtoSave.setPhone(customerPhone);
				objCustomerDtoSave.setPhone2(customerPhone);
				objCustomerDtoSave.setOldId(customerId);
				objCustomerDtoSave.setCreatedAt(DateTimeUtil.getCurrentDateTime());
				objCustomerDtoSave.setUpdatedAt(DateTimeUtil.getCurrentDateTime());
				customerService.createCustomerInIparkingCenter(objCustomerDtoSave);

			}
			objTicketDto.setCustomer(customerId);
			// thuc hiện kiểm tra email verfify
			if (!StringUtils.isEmpty(email)) {
				emailService.checkAndsendMailActiveASynchronous(customerId, customerPhone, email);
			}

			objTicketDto.setParkingPlace(parkingPlace);
			objTicketDto.setCarNumberPlate(carNumberPlate);
			objTicketDto.setCarType(carType);
			objTicketDto.setCarPricingGroup(carPricingGroup);
			objTicketDto.setPaidAmount(0);
			objTicketDto.setMustPayAmount(0);
			objTicketDto.setStatus(0);
			objTicketDto.setTicketData("");
			objTicketDto.setInSession(Boolean.TRUE);
			objTicketDto.setCreatedAt(DateTimeUtil.getCurrentDateTime());
			objTicketDto
					.setUpdatedAt(Utils.getDatetimeFormatVN(Calendar.getInstance().getTime(), "yyyy-MM-dd HH:mm:ss"));

			objTicketDto = ticketService.save(objTicketDto);
			responseDto.setData(objTicketDto);
			return responseDto;
		} catch (Exception ex) {
			LOGGER.error("Loi he thong :" + ex.getMessage());
			responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
			responseDto.setMessage("Lỗi hệ thống:" + ex.getMessage());
			return responseDto;
		}

	}

}

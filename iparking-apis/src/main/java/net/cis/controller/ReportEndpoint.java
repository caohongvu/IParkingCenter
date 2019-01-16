package net.cis.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
import net.cis.common.util.DateDto;
import net.cis.common.util.DateTimeUtil;
import net.cis.common.util.StatusUtil;
import net.cis.constants.ResponseErrorCodeConstants;
import net.cis.dto.ErrorDto;
import net.cis.dto.ParkingActorDto;
import net.cis.dto.ParkingContractDto;
import net.cis.dto.ParkingDto;
import net.cis.dto.ParkingInfoDto;
import net.cis.dto.PerformanceExtractionDto;
import net.cis.dto.ProportionPaymentDto;
import net.cis.dto.ResponseApi;
import net.cis.jpa.criteria.DailyTicketPaymentCriteria;
import net.cis.jpa.criteria.MonthlyTicketPaymentCriteria;
import net.cis.jpa.criteria.ParkingContractCriteria;
import net.cis.jpa.criteria.TicketDailyCriteria;
import net.cis.security.filter.TokenAuthenticationService;
import net.cis.service.DailyTicketPaymentService;
import net.cis.service.MonthlyTicketPaymentService;
import net.cis.service.ParkingActorService;
import net.cis.service.ParkingContractService;
import net.cis.service.ParkingInfoService;
import net.cis.service.ParkingService;
import net.cis.service.ReportDelegatePaymentService;
import net.cis.service.TicketDailyPortalService;

@RestController
@RequestMapping("/report")
@Api(value = "report Endpoint", description = "The URL to handle report endpoint")
public class ReportEndpoint {
	protected final Logger LOGGER = Logger.getLogger(getClass());
	@Autowired
	private DailyTicketPaymentService dailyTicketPaymentService;

	@Autowired
	private ReportDelegatePaymentService reportDelegatePaymentService;

	@Autowired
	private TicketDailyPortalService ticketDailyPortalService;

	@Autowired
	private MonthlyTicketPaymentService monthlyTicketPaymentService;

	@Autowired
	private ParkingActorService parkingActorService;

	@Autowired
	private ParkingService parkingService;

	@Autowired
	private ParkingInfoService parkingInfoService;

	@Autowired
	private ParkingContractService parkingContractService;

	@RequestMapping(value = "/daily/ticket/payment", method = RequestMethod.GET)
	@ApiOperation("Fetch all ticket payment")
	public @ResponseBody Object fetchTicketsPayment(@RequestParam(name = "orderID", required = false) String orderID,
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

		Date toDate = new Date(end_time * 1000L);

		Date fromDate = new Date(start_time * 1000L);

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

		Date toDate = new Date(end_time * 1000L);

		Date fromDate = new Date(start_time * 1000L);

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
			ticketCriteria.setContract_no(contract_no);
			;
		}

		if (contract_code != null && contract_code != "") {
			ticketCriteria.setContract_code(contract_code);
		}

		if (period_payment != null && period_payment != "") {
			ticketCriteria.setPeriodPayment(period_payment);
			;
		}

		ticketCriteria.setStart_time(formatTime.format(fromDate));
		ticketCriteria.setEnd_time(formatTime.format(toDate));

		Pageable pageable = new PageRequest(page, size);
		ResponseApi enpoint = monthlyTicketPaymentService.findAll(ticketCriteria, pageable);

		return enpoint;
	}

	@RequestMapping(value = "/supervisor/delegate/payment", method = RequestMethod.GET)
	@ApiOperation("Fetch all delegate ticket payment")
	public @ResponseBody ResponseApi fetchDelegateTicketsPayment(HttpServletRequest request,
			@RequestParam("date") String date) {
		ResponseApi response = new ResponseApi();

		List<Long> carppIds = null;
		String supervisorId = TokenAuthenticationService.getAuthenticationInfo(request);

		List<ParkingActorDto> parkingActors = parkingActorService.findByActors(Long.valueOf(supervisorId));

		carppIds = parkingActors.stream().map(ParkingActorDto::getCppId).collect(Collectors.toList());

		ErrorDto error = new ErrorDto();
		error.setCode(StatusUtil.SUCCESS_STATUS);
		error.setMessage(StatusUtil.SUCCESS_MESSAGE);
		response.setData(reportDelegatePaymentService.findByCarppIdsAndDate(carppIds, date));
		response.setError(error);

		return response;
	}

	@RequestMapping(value = "/daily/ticket", method = RequestMethod.GET)
	@ApiOperation("Fetch all ticket daily")
	public @ResponseBody Object fetchDailyTicket(HttpServletRequest request, @RequestParam(name = "cpp_code", required = false) String cppCode,
			@RequestParam(name = "number_plate", required = false) String numberPlate,
			@RequestParam(name = "phone", required = false) Long phone,
			@RequestParam(name = "start_time", required = false) Long start_time,
			@RequestParam(name = "end_time", required = false) Long end_time,
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@RequestParam(name = "size", required = false, defaultValue = "500") int size) {

		SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date endDate = new Date(end_time * 1000L);

		Date fromDate = new Date(start_time * 1000L);

		page = page - 1;
		if (page < 0) {
			page = 0;
		}

		TicketDailyCriteria ticketDailyCriteria = new TicketDailyCriteria();

		
		if (phone != null && phone != 0) {
			ticketDailyCriteria.setPhone2(phone);
		}

		if (numberPlate != null && numberPlate != "") {
			ticketDailyCriteria.setNumberplate(numberPlate.toUpperCase());
		}
		ticketDailyCriteria.setStart_time(formatTime.format(fromDate));
		ticketDailyCriteria.setTo_time(formatTime.format(endDate));
		int role = Integer.parseInt(TokenAuthenticationService.getAuthenticationRole(request));
		

		if (role==1) {
			if (cppCode != null && cppCode != "") {
				ticketDailyCriteria.setCppCode(cppCode.toUpperCase());
			}
		} else if (role == 2) {
			Long userId = Long.parseLong(TokenAuthenticationService.getAuthenticationInfo(request));
			List<ParkingActorDto> parkingActorDtos = parkingActorService.findByActors(userId);
			ParkingDto objParkingDto = parkingService.findById(parkingActorDtos.get(0).getId());		
			ticketDailyCriteria.setCppCode(objParkingDto.getParkingCode());
		}
		Pageable pageable = new PageRequest(page, size);
		ResponseApi enpoint =  ticketDailyPortalService.getAllTicketDailyFooterPortal(ticketDailyCriteria, pageable);

		return enpoint;

	}
	

	/**
	 * liemnh
	 * 
	 * @param request
	 * @param cppCode
	 * @param fromDate
	 * @param toDate
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/supervisor/performance/extraction", method = RequestMethod.GET)
	@ApiOperation("Hieu xuat khai thac")
	public @ResponseBody ResponseApi fetchPerformanceExtraction(HttpServletRequest request,
			@RequestParam(name = "cpp_code", required = false) String cppCode,
			@RequestParam(name = "from_date") String fromDate, @RequestParam(name = "to_date") String toDate)
			throws ParseException {
		ResponseApi response = new ResponseApi();
		ErrorDto error = new ErrorDto();
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat simpleDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (simpleDate.parse(fromDate).compareTo(DateTimeUtil.getCurrentDateTime()) > 0) {
			error.setCode(StatusUtil.FAIL_STATUS);
			error.setMessage(StatusUtil.FAIL_MESSAGE);
			response.setError(error);
			return response;
		}
		if (simpleDate.parse(toDate).compareTo(DateTimeUtil.getCurrentDateTime()) > 0) {
			error.setCode(StatusUtil.FAIL_STATUS);
			error.setMessage(StatusUtil.FAIL_MESSAGE);
			response.setError(error);
			return response;
		}
		String supervisorId = TokenAuthenticationService.getAuthenticationInfo(request);
		int actorId = Integer.parseInt(supervisorId);
		List<ParkingActorDto> parkingActorDtos = parkingActorService.findByActors(actorId);
		List<Long> lstCppId = new ArrayList<>();
		parkingActorDtos.stream().forEach(item -> lstCppId.add(item.getCppId()));
		if (!StringUtils.isEmpty(cppCode)) {
			// thuc hien kiem tra parkingCode co nam trong quyen cua user super
			ParkingDto objParkingDto = parkingService.findByParkingCode(cppCode);
			if (objParkingDto != null && lstCppId.contains(Long.parseLong(objParkingDto.getOldId()))) {
				lstCppId.clear();
				lstCppId.add(Long.parseLong(objParkingDto.getOldId()));
			}
		}
		Date date1 = simpleDate.parse(fromDate);
		Date date2 = simpleDate.parse(toDate);
		long timediff = (TimeUnit.DAYS.convert(date2.getTime() - date1.getTime(), TimeUnit.MILLISECONDS) + 1);
		List<DateDto> lstDateDto = DateTimeUtil.getDayOfMonths(date1, date2);
		List<PerformanceExtractionDto> result = new ArrayList<>();
		// thuc hien tinh toan doanh thu ve luot va ve thang
		for (Long cppId : lstCppId) {
			ParkingDto objParkingDto = parkingService.findByOldId(String.valueOf(cppId));
			ParkingInfoDto objParkingInfoDto = parkingInfoService.findByCppId(cppId);
			double mauthuc = objParkingDto.getCapacity() * objParkingInfoDto.getTimeAvg()
					* Long.valueOf(timediff).doubleValue() * 25000;
			LOGGER.info("capacity: " + objParkingDto.getCapacity());
			LOGGER.info("timeAvg: " + objParkingInfoDto.getTimeAvg());
			LOGGER.info("timediff: " + Long.valueOf(timediff).doubleValue());
			double revenuVeluot = 0d;
			double revenuVeThang = 0d;
			LOGGER.info("MauThuc: " + Double.toString(mauthuc));

			PerformanceExtractionDto objPerformance = new PerformanceExtractionDto();
			objPerformance.setCppId(cppId);
			objPerformance.setParkingCode(objParkingDto.getParkingCode());
			objPerformance.setAddress(objParkingDto.getAddress());

			if (objParkingDto != null) {
				// thuc hien lay doanh thu ve luot
				LOGGER.info("veluot: " + simpleDateTime.format(DateTimeUtil.getCurrentDateTime()));
				ProportionPaymentDto objProportionPaymentDto = reportDelegatePaymentService.getProportionPayment(cppId,
						fromDate, toDate);
				LOGGER.info("veluot: " + simpleDateTime.format(DateTimeUtil.getCurrentDateTime()));
				revenuVeluot = objProportionPaymentDto.getRevenue();
				// tinh toan doanh thu ve thang
				ParkingContractCriteria ticketCriteria = new ParkingContractCriteria();
				ticketCriteria.setCppCode(cppCode);
				ticketCriteria.setFromDate(simpleDate.parse(fromDate));
				ticketCriteria.setToDate(simpleDate.parse(toDate));
				LOGGER.info("vethang: " + simpleDateTime.format(DateTimeUtil.getCurrentDateTime()));
				List<ParkingContractDto> lstParkingContract = parkingContractService.findAll(ticketCriteria);
				LOGGER.info("vethang: " + simpleDateTime.format(DateTimeUtil.getCurrentDateTime()));
				double totalRevenu = 0d;
				if (lstParkingContract != null) {
					for (ParkingContractDto objParkingContractDto : lstParkingContract) {
						totalRevenu += objParkingContractDto.getMonthlyUnitPrice();
					}
				}
				for (DateDto objDateDto : lstDateDto) {
					revenuVeThang += (objDateDto.getDay() * totalRevenu) / objDateDto.getDayOfMonth();
				}

				LOGGER.info("revenuVeluot: " + Double.toString(revenuVeluot));
				LOGGER.info("revenuVeThang: " + Double.toString(revenuVeThang));
				LOGGER.info("performance: " + Double.toString((revenuVeluot + revenuVeThang) / mauthuc));
				objPerformance.setPerformance(((revenuVeluot + revenuVeThang) / mauthuc));
			}
			result.add(objPerformance);
		}
		error.setCode(StatusUtil.SUCCESS_STATUS);
		error.setMessage(StatusUtil.SUCCESS_MESSAGE);
		response.setError(error);
		response.setData(result);
		return response;
	}

	/**
	 * liemnh
	 * 
	 * @param request
	 * @param cppCode
	 * @param fromDate
	 * @param toDate
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/supervisor/proportion/payment", method = RequestMethod.GET)
	@ApiOperation("Ty trong thanh toan")
	public @ResponseBody ResponseApi fetchProportionPayment(HttpServletRequest request,
			@RequestParam(name = "cpp_code", required = false) String cppCode,
			@RequestParam(name = "from_date") String fromDate, @RequestParam(name = "to_date") String toDate)
			throws ParseException {
		ResponseApi response = new ResponseApi();
		ErrorDto error = new ErrorDto();
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
		if (simpleDate.parse(fromDate).compareTo(DateTimeUtil.getCurrentDateTime()) > 0) {
			error.setCode(StatusUtil.FAIL_STATUS);
			error.setMessage(StatusUtil.FAIL_MESSAGE);
			response.setError(error);
			return response;
		}
		if (simpleDate.parse(toDate).compareTo(DateTimeUtil.getCurrentDateTime()) > 0) {
			error.setCode(StatusUtil.FAIL_STATUS);
			error.setMessage(StatusUtil.FAIL_MESSAGE);
			response.setError(error);
			return response;
		}
		String supervisorId = TokenAuthenticationService.getAuthenticationInfo(request);
		// laay thoong tin diem do cuar supervisorId
		int actorId = Integer.parseInt(supervisorId);
		List<ParkingActorDto> parkingActorDtos = parkingActorService.findByActors(actorId);
		List<Long> lstCppCode = new ArrayList<>();
		parkingActorDtos.stream().forEach(item -> lstCppCode.add(item.getCppId()));
		List<ProportionPaymentDto> result = new ArrayList<>();
		if (!StringUtils.isEmpty(cppCode)) {
			// thuc hien kiem tra parkingCode co nam trong quyen cua user super
			ParkingDto objParkingDto = parkingService.findByParkingCode(cppCode);
			if (objParkingDto != null && lstCppCode.contains(Long.parseLong(objParkingDto.getOldId()))) {
				lstCppCode.clear();
				lstCppCode.add(Long.parseLong(objParkingDto.getOldId()));
			}
		}
		result = reportDelegatePaymentService.getProportionPayment(lstCppCode, fromDate, toDate);
		error.setCode(StatusUtil.SUCCESS_STATUS);
		error.setMessage(StatusUtil.SUCCESS_MESSAGE);
		response.setError(error);
		response.setData(calculateProportionPayment(result));
		return response;

	}

	private List<ProportionPaymentDto> calculateProportionPayment(List<ProportionPaymentDto> lstProportionPaymentDto) {
		List<ProportionPaymentDto> result = new ArrayList<>();
		lstProportionPaymentDto.stream().forEach(item -> {
			ProportionPaymentDto objResult = new ProportionPaymentDto();
			objResult.setId(item.getId());
			objResult.setCode(item.getCode());
			objResult.setCpp_id(item.getCpp_id());
			objResult.setAddress(item.getAddress());
			objResult.setCapacity(item.getCapacity());
			objResult.setProvider_id(item.getProvider_id());
			objResult.setRevenue(item.getRevenue());
			objResult.setAtm((item.getAtm() / item.getRevenue()) * 100);
			objResult.setSms((item.getSms() / item.getRevenue()) * 100);
			objResult.setBalance((item.getBalance() / item.getRevenue()) * 100);
			objResult.setBalance_tth((item.getBalance_tth() / item.getRevenue()) * 100);
			objResult.setVisa_master_tth((item.getVisa_master_tth() / item.getRevenue()) * 100);
			objResult.setVisa_master_kh((item.getVisa_master_kh() / item.getRevenue()) * 100);
			objResult.setCash((item.getCash() / item.getRevenue()) * 100);
			result.add(objResult);
		});
		return result;
	}

	/**
	 * liemnh Laays doanh thu ve luot theo diem do hoac cong ty
	 * 
	 * @param cppCode
	 * @param start_time
	 * @param end_time
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/daily/ticket/revenue", method = RequestMethod.GET)
	@ApiOperation("Doanh thu ve luot theo diem do hoac cong ty")
	public @ResponseBody Object fetchDailyTicketsRevenueGroupByParking(@RequestParam(name = "code") String cppCode,
			@RequestParam(name = "from_time") Long start_time, @RequestParam(name = "end_time") Long end_time,
			@RequestParam(name = "type") int type) throws Exception {

		try {
			DailyTicketPaymentCriteria ticketCriteria = new DailyTicketPaymentCriteria();
			SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date toDate = new Date(end_time * 1000L);
			Date fromDate = new Date(start_time * 1000L);
			ticketCriteria.setStart_time(formatTime.format(fromDate));
			ticketCriteria.setEnd_time(formatTime.format(toDate));
			ticketCriteria.setCppCode(cppCode);

			if (type == 1) {
				return dailyTicketPaymentService.getRevenueGroupByParkingCodeSP(ticketCriteria);
			} else {
				return dailyTicketPaymentService.getRevenueGroupByCompanyCodeSP(ticketCriteria);
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			ResponseApi responseApi = new ResponseApi();
			ErrorDto errorDto = new ErrorDto();
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage(ex.getMessage());
			responseApi.setError(errorDto);
			return responseApi;
		}

	}

	/**
	 * liemnh Laays doanh thu tháng theo diem do hoac cong ty
	 * 
	 * @param cppCode
	 * @param start_time
	 * @param end_time
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/monthly/ticket/revenue", method = RequestMethod.GET)
	@ApiOperation("Doanh thu ve thang theo diem do hoac cong ty")
	public @ResponseBody Object fetchMonthlyTicketsRevenueGroupByParking(@RequestParam(name = "code") String cppCode,
			@RequestParam(name = "from_time") Long start_time, @RequestParam(name = "end_time") Long end_time,
			@RequestParam(name = "type") int type) throws Exception {
		try {
			MonthlyTicketPaymentCriteria ticketCriteria = new MonthlyTicketPaymentCriteria();
			SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date toDate = new Date(end_time * 1000L);
			Date fromDate = new Date(start_time * 1000L);
			ticketCriteria.setStart_time(formatTime.format(fromDate));
			ticketCriteria.setEnd_time(formatTime.format(toDate));
			ticketCriteria.setCppCode(cppCode);
			if (type == 1) {
				return monthlyTicketPaymentService.getRevenueGroupByParkingCodeSP(ticketCriteria);
			} else {
				return monthlyTicketPaymentService.getRevenueGroupByCompanyCodeSP(ticketCriteria);
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			ResponseApi responseApi = new ResponseApi();
			ErrorDto errorDto = new ErrorDto();
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage(ex.getMessage());
			responseApi.setError(errorDto);
			return responseApi;
		}

	}
}

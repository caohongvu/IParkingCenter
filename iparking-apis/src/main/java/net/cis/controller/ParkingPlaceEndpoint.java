package net.cis.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
import net.cis.common.util.StatusUtil;
import net.cis.common.util.constant.TicketConstants;
import net.cis.constants.ResponseErrorCodeConstants;
import net.cis.dto.CompanyDto;
import net.cis.dto.ErrorDto;
import net.cis.dto.HistoryParkingDto;
import net.cis.dto.ParkingActorDto;
import net.cis.dto.ParkingDto;
import net.cis.dto.ParkingSynDto;
import net.cis.dto.ResponseApi;
import net.cis.dto.TicketDto;
import net.cis.dto.UserDto;
import net.cis.jpa.criteria.TicketCriteria;
import net.cis.jpa.entity.CompanyInforEntity;
import net.cis.security.filter.TokenAuthenticationService;
import net.cis.service.CompanyService;
import net.cis.service.HistoryParkingService;
import net.cis.service.ParkingActorService;
import net.cis.service.ParkingService;
import net.cis.service.TicketService;
import net.cis.service.UserService;
import net.cis.service.cache.CompanyInfoCache;

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

	@Autowired
	CompanyInfoCache companyInfoCache;

	@Autowired
	ParkingActorService parkingActorService;

	@Autowired
	HistoryParkingService parkingPlaceHistoryService;

	@Autowired
	UserService userService;

	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	@RequestMapping(value = "/{id}/getByOldId", method = RequestMethod.GET)
	public @ResponseBody ParkingDto c(@PathVariable("id") String oldId) throws Exception {
		ParkingDto parkingDto = parkingService.findByOldId(oldId);
		TicketCriteria ticketCriteria = new TicketCriteria();
		ticketCriteria.setCppId(parkingDto.getOldId());
		ticketCriteria.setInSession(1);
		ticketCriteria.setStatus(TicketConstants.PAID_TICKET);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		Date toDate = calendar.getTime();
		ticketCriteria.setToDate(toDate);
		LOGGER.info("----------------getByOldId -toDate:" + format.format(toDate));
		calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.HOUR, -36);
		Date fromDate = calendar.getTime();
		LOGGER.info("---------------getByOldId-fromDate:" + format.format(fromDate));

		ticketCriteria.setFromDate(fromDate);

		Pageable pageable = new PageRequest(0, 1000);
		List<TicketDto> ticketsInsession = ticketService.findAll(ticketCriteria, pageable);
		parkingDto.setCurrentTicketInSession(ticketsInsession.size());
		CompanyInforEntity companyEntity = companyInfoCache.get(parkingDto.getCompany());
		boolean isProvideEInvoice = false;
		if (companyEntity != null && (companyEntity.getDailyInvoice() == 1 || companyEntity.getMonthlyInvoice() == 1)) {
			isProvideEInvoice = true;
		}
		parkingDto.setIsProvideEInvoice(isProvideEInvoice);
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
	 * liemnh danh sach diem do theo cong ty
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
			errorDto.setMessage(ex.getMessage());
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

	// GET DETAIL FOR PORTAL
	@RequestMapping(value = "/detail/cpp", method = RequestMethod.GET)
	public @ResponseBody ResponseApi getByOldIdToken(HttpServletRequest request) throws Exception {

		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();

		String supervisorId = TokenAuthenticationService.getAuthenticationInfo(request);

		if (StringUtils.isEmpty(supervisorId)) {
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage("Authentication faile!");
			responseApi.setError(errorDto);
			return responseApi;
		}

		// lấy thông tin cppCode mà user quản l
		List<ParkingActorDto> parkingActorDtos = parkingActorService.findByActors(Long.parseLong(supervisorId));
		if (parkingActorDtos == null || parkingActorDtos.size() == 0) {
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage("User chưa được cấu hình điểm dịch vụ");
			responseApi.setError(errorDto);
			return responseApi;
		}
		ParkingDto objParkingDto = parkingService.findByOldId(String.valueOf(parkingActorDtos.get(0).getCppId()));
		if (objParkingDto == null) {
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage("Điểm dịch vụ không tồn tại");
			responseApi.setError(errorDto);
			return responseApi;
		}

		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		errorDto.setMessage("");
		responseApi.setError(errorDto);
		responseApi.setData(objParkingDto);

		return responseApi;
	}

	// UPDATE CPP FOR PORTAL
	@RequestMapping(value = "/update/cpp", method = RequestMethod.POST)
	@ApiOperation("update account cpp")
	public @ResponseBody ResponseApi updateParkingPlace(HttpServletRequest request,
			@RequestParam(name = "cppName") String cppName,
			@RequestParam(name = "address", required = false) String address,
			@RequestParam(name = "hotline", required = false) String hotline) throws Exception {
		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();
		String supervisorId = TokenAuthenticationService.getAuthenticationInfo(request);

		if (StringUtils.isEmpty(supervisorId)) {
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage("Authentication faile!");
			responseApi.setError(errorDto);
			return responseApi;
		}

		// lấy thông tin cppCode mà user quản l
		List<ParkingActorDto> parkingActorDtos = parkingActorService.findByActors(Long.parseLong(supervisorId));
		if (parkingActorDtos == null || parkingActorDtos.size() == 0) {
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage("User chưa được cấu hình điểm dịch vụ");
			responseApi.setError(errorDto);
			return responseApi;
		}
		ParkingDto objParkingDto = parkingService.findByOldId(String.valueOf(parkingActorDtos.get(0).getCppId()));
		if (objParkingDto == null) {
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage("Điểm dịch vụ không tồn tại");
			responseApi.setError(errorDto);
			return responseApi;
		}
		HistoryParkingDto history = new HistoryParkingDto();
		String infoChange = "";
		//
		if (!objParkingDto.getAddress().equals(address)) {
			infoChange += address + ",";
		} else if (address == "" && objParkingDto.getAddress() != "") {
			infoChange += "Đã xoá " + address + ",";
		}
		//
		if (objParkingDto.getPhone().equals(hotline)) {
			infoChange += hotline + ",";
		} else if (hotline == "" && objParkingDto.getPhone() != "") {
			infoChange += "Đã xoá " + hotline + ",";
		}
		// cppName
		if (!objParkingDto.getParkingName().equals(cppName)) {
			infoChange += cppName + ",";
		} else if (cppName == "" && objParkingDto.getParkingName() != "") {
			infoChange += "Đã xoá " + cppName + ",";
		}
		System.out.println(infoChange);

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		// get username update
		UserDto res = userService.findById(Integer.parseInt(supervisorId));

		String userName = res.getUsername();

		history.setOldId(String.valueOf(objParkingDto.getOldId()));
		history.setInfoUpdate(infoChange);
		history.setUpdatedAt(dateFormat.format(date));
		history.setUserName(userName);

		objParkingDto.setAddress(address);
		objParkingDto.setParkingName(cppName);
		objParkingDto.setPhone(hotline);

		ParkingDto obj = new ParkingDto();
		try {
			obj = parkingService.updateParkingPlace(objParkingDto);
			parkingPlaceHistoryService.save(history);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage("");
			responseApi.setError(errorDto);
			return responseApi;
		}
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		errorDto.setMessage("");
		responseApi.setError(errorDto);
		responseApi.setData(obj);

		return responseApi;

	}

	// GET LIST HISTORY UPDATE FOR PORTAL
	@RequestMapping(value = "/history/cpp", method = RequestMethod.GET)
	public @ResponseBody ResponseApi getListHistoryByCppId(HttpServletRequest request) throws Exception {

		ResponseApi responseApi = new ResponseApi();
		ErrorDto errorDto = new ErrorDto();

		String supervisorId = TokenAuthenticationService.getAuthenticationInfo(request);

		if (StringUtils.isEmpty(supervisorId)) {
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage("Authentication faile!");
			responseApi.setError(errorDto);
			return responseApi;
		}

		// lấy thông tin cppCode mà user quản l
		List<ParkingActorDto> parkingActorDtos = parkingActorService.findByActors(Long.parseLong(supervisorId));
		if (parkingActorDtos == null || parkingActorDtos.size() == 0) {
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage("User chưa được cấu hình điểm dịch vụ");
			responseApi.setError(errorDto);
			return responseApi;
		}
		ParkingDto objParkingDto = parkingService.findByOldId(String.valueOf(parkingActorDtos.get(0).getCppId()));
		if (objParkingDto == null) {
			errorDto.setCode(ResponseErrorCodeConstants.StatusBadRequest);
			errorDto.setMessage("Điểm dịch vụ không tồn tại");
			responseApi.setError(errorDto);
			return responseApi;
		}

		List<HistoryParkingDto> listHistory = parkingPlaceHistoryService
				.findByOldId(String.valueOf(objParkingDto.getOldId()));

		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		errorDto.setMessage("");
		responseApi.setError(errorDto);
		responseApi.setData(listHistory);

		return responseApi;
	}

	// CREATE PARKING PLACE
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ApiOperation("create parking place")
	public @ResponseBody Object create(ParkingSynDto parkingSynDto) throws Exception {

		ResponseApi endpoint = new ResponseApi();

		ParkingDto parkingDto = new ParkingDto();

		ParkingSynDto parking = parkingService.create(parkingSynDto);

		endpoint.setData(parking);

		return endpoint;
	}

	// UPDATE ASSIGN PROVIDER PARKING PLACE
	@RequestMapping(value = "/assign/provider", method = RequestMethod.POST)
	@ApiOperation("assign parking place")
	public @ResponseBody Object assignProvider(ParkingSynDto parkingSynDto) throws Exception {

		ResponseApi endpoint = new ResponseApi();

		ParkingDto parkingDto = new ParkingDto();

		ParkingSynDto parking = parkingService.updateAssignProvider(parkingSynDto);

		endpoint.setData(parking);

		return endpoint;
	}

	// UPDATE PARKING PLACE
	@RequestMapping(value = "/update/parking_place", method = RequestMethod.POST)
	@ApiOperation("add perm parking place")
	public @ResponseBody Object addPerm(@RequestBody(required = false) ParkingSynDto parkingSynDtoRes,
			ParkingSynDto parkingSynDto) throws Exception {

		ResponseApi endpoint = new ResponseApi();
		if (parkingSynDtoRes != null) {
			if (parkingSynDtoRes.getListPayment() != null) {
				parkingSynDto.setListPayment(parkingSynDtoRes.getListPayment());
			}
		}

		ParkingSynDto parking = parkingService.updateParkingPlace(parkingSynDto);

		endpoint.setData(parking);

		return endpoint;
	}
}

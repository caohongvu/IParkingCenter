package net.cis.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cis.common.util.DateTimeUtil;
import net.cis.dto.ReportProportionPaymentDto;
import net.cis.jpa.entity.ParkingConfigEntity;
import net.cis.repository.ParkingConfigRepository;
import net.cis.service.EmailService;
import net.cis.service.JobService;
import net.cis.service.ReportProportionPaymentService;

@Service
public class JobServiceImpl implements JobService {

	protected final Logger LOGGER = Logger.getLogger(getClass());
	@Autowired
	private EntityManager entityManager;

	@Autowired
	ParkingConfigRepository parkingConfigRepository;

	@Autowired
	EmailService emailService;

	@Autowired
	ReportProportionPaymentService reportProportionPaymentService;

	/**
	 * thuc hien tinh toan doanh thu ve luot theo ngay
	 */
	@Override
	public void getDalyRevenue() {
		ParkingConfigEntity objParkingConfig = parkingConfigRepository.findByConfigKey("JOB_DAILY_REVENUE");
		// chỉ chạy JOB đến trước ngày hiện tại
		if (objParkingConfig != null && objParkingConfig.getLastDateRun().before(DateTimeUtil.getCurrentDateTime())) {
			LOGGER.info("getDalyRevenue Date: " + objParkingConfig.getLastDateRun());
			LOGGER.info("getDalyRevenue Date long: " + objParkingConfig.getLastDateRun().getTime());
			StoredProcedureQuery storedProcedureQuery = entityManager
					.createStoredProcedureQuery("calculate_daily_revenue");
			storedProcedureQuery.registerStoredProcedureParameter("day_request", Long.class, ParameterMode.IN);
			storedProcedureQuery.setParameter("day_request", objParkingConfig.getLastDateRun().getTime() / 1000);
			storedProcedureQuery.execute();
			Calendar c = Calendar.getInstance();
			c.setTime(objParkingConfig.getLastDateRun());
			c.add(Calendar.DATE, 1);
			objParkingConfig.setLastDateRun(c.getTime());
			parkingConfigRepository.save(objParkingConfig);
		}
	}

	/**
	 * thuc hien tinh toan doanh thu ve thang theo ngay
	 */
	@Override
	public void getMonthRevenue() {
		ParkingConfigEntity objParkingConfig = parkingConfigRepository.findByConfigKey("JOB_MONTHLY_REVENUE");
		// chỉ chạy JOB đến trước ngày hiện tại
		if (objParkingConfig != null && objParkingConfig.getLastDateRun().before(DateTimeUtil.getCurrentDateTime())) {
			LOGGER.info("getMonthRevenue Date: " + objParkingConfig.getLastDateRun());
			LOGGER.info("getMonthRevenue Date long: " + objParkingConfig.getLastDateRun().getTime());
			StoredProcedureQuery storedProcedureQuery = entityManager
					.createStoredProcedureQuery("calculate_monthly_revenue");
			storedProcedureQuery.registerStoredProcedureParameter("day_request", Long.class, ParameterMode.IN);
			storedProcedureQuery.setParameter("day_request", objParkingConfig.getLastDateRun().getTime() / 1000);
			storedProcedureQuery.execute();
			Calendar c = Calendar.getInstance();
			c.setTime(objParkingConfig.getLastDateRun());
			c.add(Calendar.DATE, 1);
			objParkingConfig.setLastDateRun(c.getTime());
			parkingConfigRepository.save(objParkingConfig);
		}
	}

	/**
	 * thuc hien tinh toan ty trong thanh toan ves luot theo ngay
	 */
	@Override
	public void getProportionPayment() {
		ParkingConfigEntity objParkingConfig = parkingConfigRepository.findByConfigKey("JOB_PROPORTION_PAYMENT");
		// chỉ chạy JOB đến trước ngày hiện tại
		if (objParkingConfig != null && objParkingConfig.getLastDateRun().before(DateTimeUtil.getCurrentDateTime())) {
			LOGGER.info("getProportionPayment Date: " + objParkingConfig.getLastDateRun());
			LOGGER.info("getProportionPayment Date long: " + objParkingConfig.getLastDateRun().getTime());
			StoredProcedureQuery storedProcedureQuery = entityManager
					.createStoredProcedureQuery("calculate_proportion_payment");
			storedProcedureQuery.registerStoredProcedureParameter("day_request", Long.class, ParameterMode.IN);
			storedProcedureQuery.setParameter("day_request", objParkingConfig.getLastDateRun().getTime() / 1000);
			storedProcedureQuery.execute();
			Calendar c = Calendar.getInstance();
			c.setTime(objParkingConfig.getLastDateRun());
			c.add(Calendar.DATE, 1);
			objParkingConfig.setLastDateRun(c.getTime());
			parkingConfigRepository.save(objParkingConfig);
		}
	}

	/**
	 * thuc hien tinh toan doanh thu ve luot cua cong ty theo ngay
	 */
	@Override
	public void getCompanyDalyRevenue() {
		ParkingConfigEntity objParkingConfig = parkingConfigRepository.findByConfigKey("JOB_COMPANY_DAILY_REVENUE");
		// chỉ chạy JOB đến trước ngày hiện tại
		if (objParkingConfig != null && objParkingConfig.getLastDateRun().before(DateTimeUtil.getCurrentDateTime())) {
			LOGGER.info("getCompanyDalyRevenue Date: " + objParkingConfig.getLastDateRun());
			LOGGER.info("getCompanyDalyRevenue Date long: " + objParkingConfig.getLastDateRun().getTime());
			StoredProcedureQuery storedProcedureQuery = entityManager
					.createStoredProcedureQuery("calculate_company_daily_revenue");
			storedProcedureQuery.registerStoredProcedureParameter("day_request", Long.class, ParameterMode.IN);
			storedProcedureQuery.setParameter("day_request", objParkingConfig.getLastDateRun().getTime() / 1000);
			storedProcedureQuery.execute();
			Calendar c = Calendar.getInstance();
			c.setTime(objParkingConfig.getLastDateRun());
			c.add(Calendar.DATE, 1);
			objParkingConfig.setLastDateRun(c.getTime());
			parkingConfigRepository.save(objParkingConfig);
		}

	}

	/**
	 * thuc hien tinh toan doanh thu ve thang cua cong ty theo ngay
	 */
	@Override
	public void getCompanyMonthRevenue() {
		// chỉ chạy JOB đến trước ngày hiện tại
		ParkingConfigEntity objParkingConfig = parkingConfigRepository.findByConfigKey("JOB_COMPANY_MONTHLY_REVENUE");
		if (objParkingConfig != null && objParkingConfig.getLastDateRun().before(DateTimeUtil.getCurrentDateTime())) {
			LOGGER.info("getCompanyMonthRevenue Date: " + objParkingConfig.getLastDateRun());
			LOGGER.info("getCompanyMonthRevenue Date long: " + objParkingConfig.getLastDateRun().getTime());
			StoredProcedureQuery storedProcedureQuery = entityManager
					.createStoredProcedureQuery("calculate_company_monthly_revenue");
			storedProcedureQuery.registerStoredProcedureParameter("day_request", Long.class, ParameterMode.IN);
			storedProcedureQuery.setParameter("day_request", objParkingConfig.getLastDateRun().getTime() / 1000);
			storedProcedureQuery.execute();
			Calendar c = Calendar.getInstance();
			c.setTime(objParkingConfig.getLastDateRun());
			c.add(Calendar.DATE, 1);
			objParkingConfig.setLastDateRun(c.getTime());
			parkingConfigRepository.save(objParkingConfig);
		}

	}

	/**
	 * Thuc hien kiem tra cac diem do khong co doanh thu T + 1
	 * 
	 * @throws Exception
	 */
	@Override
	public void checkWaringNoRevenue() {
		/**
		 * thu hien kiem tra doanh thu cua tung diem đõ , nếu điểm đỗ nào có
		 * không có doanh thu thì thông báo gửi email
		 */
		SimpleDateFormat simple = new SimpleDateFormat("dd/MM/yyyy");
		Date currentDate = DateTimeUtil.getCurrentDateTime();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.add(Calendar.DATE, -1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date dateBefore1Days = cal.getTime();

		LOGGER.info("----------------dateBefore1Days job ----------------- :" + dateBefore1Days.getTime() / 1000);
		List<ReportProportionPaymentDto> lstProportionPaymentDto = reportProportionPaymentService
				.getProportionPaymentByDay(dateBefore1Days.getTime() / 1000, dateBefore1Days.getTime() / 1000);
		LOGGER.info("----------------Start job checkWaringNoRevenue -----------------");
		StringBuilder emailContent = new StringBuilder();
		emailContent.append("Danh sách công ty không phát sinh doanh thu ngày: ")
				.append(simple.format(dateBefore1Days));
		String title = emailContent.toString();
		emailContent.append(" <br />\n");
		if (lstProportionPaymentDto != null && lstProportionPaymentDto.size() > 0) {
			for (ReportProportionPaymentDto obj : lstProportionPaymentDto) {
				emailContent.append("<strong> - ");
				emailContent.append(obj.getParkingCode()).append(" - ").append(obj.getCompany());
				emailContent.append(" </strong> <br />\n");
			}
			try {
				emailService.sendMailCheckWaringNoRevenue(title, emailContent.toString());
			} catch (Exception ex) {
				LOGGER.error(ex.getMessage());
			}
		}
		LOGGER.info("----------------End job checkWaringNoRevenue-----------------");

	}
}

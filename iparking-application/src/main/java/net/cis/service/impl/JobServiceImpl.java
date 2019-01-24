package net.cis.service.impl;

import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cis.common.util.DateTimeUtil;
import net.cis.jpa.entity.ParkingConfigEntity;
import net.cis.repository.ParkingConfigRepository;
import net.cis.service.JobService;

@Service
public class JobServiceImpl implements JobService {

	protected final Logger LOGGER = Logger.getLogger(getClass());
	@Autowired
	private EntityManager entityManager;

	@Autowired
	ParkingConfigRepository parkingConfigRepository;

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
}

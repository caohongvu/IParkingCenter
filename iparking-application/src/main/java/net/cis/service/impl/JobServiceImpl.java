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

	@Override
	public void getDalyRevenue() {
		ParkingConfigEntity objParkingConfig = parkingConfigRepository.findByConfigKey("JOB_DAILY_REVENUE");
		if (objParkingConfig != null && objParkingConfig.getLastDateRun().before(DateTimeUtil.getCurrentDateTime()) ) {
			LOGGER.info("Date: " + objParkingConfig.getLastDateRun() );
			LOGGER.info("Date long: " + objParkingConfig.getLastDateRun().getTime() );
			StoredProcedureQuery storedProcedureQuery = entityManager
					.createStoredProcedureQuery("calculate_daily_revenue");
			storedProcedureQuery.registerStoredProcedureParameter("day_request", Long.class, ParameterMode.IN);
			storedProcedureQuery.setParameter("day_request", objParkingConfig.getLastDateRun().getTime() /1000);
			storedProcedureQuery.execute();
			//storedProcedureQuery.getResultList();

			Calendar c = Calendar.getInstance();
			c.setTime(objParkingConfig.getLastDateRun());
			c.add(Calendar.DATE, 1);
			objParkingConfig.setLastDateRun(c.getTime());
			parkingConfigRepository.save(objParkingConfig);
		}

	}
}

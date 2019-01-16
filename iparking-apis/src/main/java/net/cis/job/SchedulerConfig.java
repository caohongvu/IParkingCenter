package net.cis.job;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import net.cis.service.JobService;
import net.cis.service.VerifyUserService;
import net.cis.utils.ParkingCenterConstants;
import net.cis.utils.PropertiesUtils;

/**
 * 
 * @author liemnh
 *
 */
@Configuration
@EnableScheduling
public class SchedulerConfig implements SchedulingConfigurer {

	protected final Logger LOGGER = Logger.getLogger(getClass());

	@Autowired
	VerifyUserService verifyUserService;

	@Autowired
	JobService jobService;

	private String cronJobEmailConfig() {
		String cronTabExpression = PropertiesUtils.getProperty("JOB_EMAIL");
		if (StringUtils.isEmpty(cronTabExpression))
			cronTabExpression = "0/5 * * * * *";
		LOGGER.info("Cron job email partten:" + cronTabExpression);
		return cronTabExpression;
	}

	private boolean cronJobEmailEnableConfig() {
		String strEnable = PropertiesUtils.getProperty("JOB_EMAIL_ENABLE");
		LOGGER.info("cronJobEmailEnableConfig: " + strEnable);
		if (StringUtils.isEmpty(strEnable)) {
			return Boolean.FALSE;
		}
		if (ParkingCenterConstants.JOB_ENABLE.equals(strEnable)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	private String cronJobPushNotificationConfig() {
		String cronTabExpression = PropertiesUtils.getProperty("JOB_PUSH_NOTIFICATION");
		if (StringUtils.isEmpty(cronTabExpression))
			cronTabExpression = "0/5 * * * * *";
		LOGGER.info("Cron job push notification partten:" + cronTabExpression);
		return cronTabExpression;
	}

	private boolean cronJobPushNotificationEnableConfig() {
		String strEnable = PropertiesUtils.getProperty("JOB_PUSH_NOTIFICATION_ENABLE");
		LOGGER.info("cronJobPushNotificationEnableConfig: " + strEnable);
		if (StringUtils.isEmpty(strEnable)) {
			return Boolean.FALSE;
		}
		if (ParkingCenterConstants.JOB_ENABLE.equals(strEnable)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	private String cronJobGetDailyRevenueConfig() {
		String cronTabExpression = PropertiesUtils.getProperty("JOB_DAILY_REVENUE");
		if (StringUtils.isEmpty(cronTabExpression))
			cronTabExpression = "0/5 * * * * *";
		LOGGER.info("Cron job get daily revenue partten:" + cronTabExpression);
		return cronTabExpression;
	}

	private boolean cronJobGetDailyRevenueEnableConfig() {
		String strEnable = PropertiesUtils.getProperty("JOB_DAILY_REVENUE_ENABLE");
		LOGGER.info("cronJobGetDailyRevenueEnableConfig: " + strEnable);
		if (StringUtils.isEmpty(strEnable)) {
			return Boolean.FALSE;
		}
		if (ParkingCenterConstants.JOB_ENABLE.equals(strEnable)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

		// job send email verify
		taskRegistrar.addTriggerTask(new Runnable() {
			@Override
			public void run() {
				if (cronJobEmailEnableConfig())
					verifyUserService.verifyEmail();
			}
		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				CronTrigger trigger = new CronTrigger(cronJobEmailConfig());
				Date nextExec = trigger.nextExecutionTime(triggerContext);
				return nextExec;
			}
		});
		// job push notifcation
		taskRegistrar.addTriggerTask(new Runnable() {
			@Override
			public void run() {
				if (cronJobPushNotificationEnableConfig())
					verifyUserService.pushNotification();
			}
		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				CronTrigger trigger = new CronTrigger(cronJobPushNotificationConfig());
				Date nextExec = trigger.nextExecutionTime(triggerContext);
				return nextExec;
			}
		});

		// job get daily revenue
		taskRegistrar.addTriggerTask(new Runnable() {
			@Override
			public void run() {
				if (cronJobGetDailyRevenueEnableConfig())
					jobService.getDalyRevenue();
			}
		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				CronTrigger trigger = new CronTrigger(cronJobGetDailyRevenueConfig());
				Date nextExec = trigger.nextExecutionTime(triggerContext);
				return nextExec;
			}
		});
	}
}
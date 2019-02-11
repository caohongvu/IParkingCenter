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

	private String cronJobVerifyEmailConfig() {
		String cronTabExpression = PropertiesUtils.getProperty("JOB_VERIFY_EMAIL");
		if (StringUtils.isEmpty(cronTabExpression))
			cronTabExpression = "0/5 * * * * *";
		LOGGER.info("Cron job email partten:" + cronTabExpression);
		return cronTabExpression;
	}

	private boolean cronJobVerifyEmailEnableConfig() {
		String strEnable = PropertiesUtils.getProperty("JOB_VERIFY_EMAIL_ENABLE");
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

	private String cronJobGetMonthlyRevenueConfig() {
		String cronTabExpression = PropertiesUtils.getProperty("JOB_MONTHLY_REVENUE");
		if (StringUtils.isEmpty(cronTabExpression))
			cronTabExpression = "0/5 * * * * *";
		LOGGER.info("Cron job get monthly revenue partten:" + cronTabExpression);
		return cronTabExpression;
	}

	private boolean cronJobGetMonthlyRevenueEnableConfig() {
		String strEnable = PropertiesUtils.getProperty("JOB_MONTHLY_REVENUE_ENABLE");
		LOGGER.info("cronJobGetMonthlyRevenueEnableConfig: " + strEnable);
		if (StringUtils.isEmpty(strEnable)) {
			return Boolean.FALSE;
		}
		if (ParkingCenterConstants.JOB_ENABLE.equals(strEnable)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	private String cronJobGetProportionPaymentConfig() {
		String cronTabExpression = PropertiesUtils.getProperty("JOB_PROPORTION_PAYMENT");
		if (StringUtils.isEmpty(cronTabExpression))
			cronTabExpression = "0/5 * * * * *";
		LOGGER.info("Cron job get proportion payment partten:" + cronTabExpression);
		return cronTabExpression;
	}

	private boolean cronJobGetProportionPaymentEnableConfig() {
		String strEnable = PropertiesUtils.getProperty("JOB_PROPORTION_PAYMENT_ENABLE");
		LOGGER.info("cronJobGetProportionPaymentEnableConfig: " + strEnable);
		if (StringUtils.isEmpty(strEnable)) {
			return Boolean.FALSE;
		}
		if (ParkingCenterConstants.JOB_ENABLE.equals(strEnable)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	private String cronJobGetCompanyDailyRevenuePaymentConfig() {
		String cronTabExpression = PropertiesUtils.getProperty("JOB_COMPANY_DAILY_REVENUE");
		if (StringUtils.isEmpty(cronTabExpression))
			cronTabExpression = "0/5 * * * * *";
		LOGGER.info("CronJobGetCompanyDailyRevenuePaymentConfig partten:" + cronTabExpression);
		return cronTabExpression;
	}

	private boolean cronJobGetCompanyDailyRevenuePaymentEnableConfig() {
		String strEnable = PropertiesUtils.getProperty("JOB_COMPANY_DAILY_REVENUE_ENABLE");
		LOGGER.info("cronJobGetCompanyDailyRevenuePaymentEnableConfig: " + strEnable);
		if (StringUtils.isEmpty(strEnable)) {
			return Boolean.FALSE;
		}
		if (ParkingCenterConstants.JOB_ENABLE.equals(strEnable)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	private String cronJobGetCompanyMonthlyRevenuePaymentConfig() {
		String cronTabExpression = PropertiesUtils.getProperty("JOB_COMPANY_MONTHLY_REVENUE");
		if (StringUtils.isEmpty(cronTabExpression))
			cronTabExpression = "0/5 * * * * *";
		LOGGER.info("CronJobGetCompanyDailyRevenuePaymentConfig partten:" + cronTabExpression);
		return cronTabExpression;
	}

	private boolean cronJobGetCompanyMonthlyRevenuePaymentEnableConfig() {
		String strEnable = PropertiesUtils.getProperty("JOB_COMPANY_MONTHLY_REVENUE_ENABLE");
		LOGGER.info("cronJobGetCompanyDailyRevenuePaymentEnableConfig: " + strEnable);
		if (StringUtils.isEmpty(strEnable)) {
			return Boolean.FALSE;
		}
		if (ParkingCenterConstants.JOB_ENABLE.equals(strEnable)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	private String cronJobCheckWarngNoRevenueConfig() {
		String cronTabExpression = PropertiesUtils.getProperty("JOB_CHECK_WARNG_NO_REVENUE");
		if (StringUtils.isEmpty(cronTabExpression))
			cronTabExpression = "0/5 * * * * *";
		LOGGER.info("cronJobCheckWarngNoRevenueConfig partten:" + cronTabExpression);
		return cronTabExpression;
	}

	private boolean cronJobCheckWarngNoRevenueEnableConfig() {
		String strEnable = PropertiesUtils.getProperty("JOB_CHECK_WARNG_NO_REVENUE_ENABLE");
		LOGGER.info("cronJobJobCheckWarngNoRevenueEnableConfig: " + strEnable);
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

		/**
		 * job send email verify den customer
		 */
		taskRegistrar.addTriggerTask(new Runnable() {
			@Override
			public void run() {
				if (cronJobVerifyEmailEnableConfig())
					verifyUserService.verifyEmail();
			}
		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				CronTrigger trigger = new CronTrigger(cronJobVerifyEmailConfig());
				Date nextExec = trigger.nextExecutionTime(triggerContext);
				return nextExec;
			}
		});
		/**
		 * job push notifcation
		 */
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

		// job get monthly revenue
		taskRegistrar.addTriggerTask(new Runnable() {
			@Override
			public void run() {
				if (cronJobGetMonthlyRevenueEnableConfig())
					jobService.getDalyRevenue();
			}
		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				CronTrigger trigger = new CronTrigger(cronJobGetMonthlyRevenueConfig());
				Date nextExec = trigger.nextExecutionTime(triggerContext);
				return nextExec;
			}
		});

		// job get Proportion Payment
		taskRegistrar.addTriggerTask(new Runnable() {
			@Override
			public void run() {
				if (cronJobGetProportionPaymentEnableConfig())
					jobService.getProportionPayment();
			}
		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				CronTrigger trigger = new CronTrigger(cronJobGetProportionPaymentConfig());
				Date nextExec = trigger.nextExecutionTime(triggerContext);
				return nextExec;
			}
		});

		// job get company daily revenue
		taskRegistrar.addTriggerTask(new Runnable() {
			@Override
			public void run() {
				if (cronJobGetCompanyDailyRevenuePaymentEnableConfig())
					jobService.getCompanyDalyRevenue();
			}
		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				CronTrigger trigger = new CronTrigger(cronJobGetCompanyDailyRevenuePaymentConfig());
				Date nextExec = trigger.nextExecutionTime(triggerContext);
				return nextExec;
			}
		});

		// job get company monthly revenue
		taskRegistrar.addTriggerTask(new Runnable() {
			@Override
			public void run() {
				if (cronJobGetCompanyMonthlyRevenuePaymentEnableConfig())
					jobService.getCompanyMonthRevenue();
			}
		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				CronTrigger trigger = new CronTrigger(cronJobGetCompanyMonthlyRevenuePaymentConfig());
				Date nextExec = trigger.nextExecutionTime(triggerContext);
				return nextExec;
			}
		});

		// job check waring no revenue
		taskRegistrar.addTriggerTask(new Runnable() {
			@Override
			public void run() {
				if (cronJobCheckWarngNoRevenueEnableConfig())
					jobService.checkWaringNoRevenue();
			}
		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				CronTrigger trigger = new CronTrigger(cronJobCheckWarngNoRevenueConfig());
				Date nextExec = trigger.nextExecutionTime(triggerContext);
				return nextExec;
			}
		});

	}
}
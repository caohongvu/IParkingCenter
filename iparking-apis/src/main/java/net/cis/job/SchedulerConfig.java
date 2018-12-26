package net.cis.job;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import net.cis.repository.ParkingConfigRepository;
import net.cis.service.EmailService;
import net.cis.utils.PropertiesUtils;

@Configuration
@EnableScheduling
public class SchedulerConfig implements SchedulingConfigurer {

	@Autowired
	ParkingConfigRepository parkingConfigRepository;
	@Autowired
	EmailService emailService;

	private String cronJobEmailConfig() {
		String cronTabExpression = "0/5 * * * * *";
		if (!StringUtils.isEmpty(PropertiesUtils.getProperty("JOB_EMAIL")))
			cronTabExpression = PropertiesUtils.getProperty("JOB_EMAIL");
		System.out.println("Cron job email:" + cronTabExpression);
		return cronTabExpression;
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.addTriggerTask(new Runnable() {
			@Override
			public void run() {
				emailService.sendEmailActive("", "liem.nguyen@cis.net.vn");
			}
		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				String cron = cronJobEmailConfig();
				CronTrigger trigger = new CronTrigger(cron);
				Date nextExec = trigger.nextExecutionTime(triggerContext);
				return nextExec;
			}
		});
	}

}
package net.cis.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cis.service.EmailService;
import net.cis.service.PushNotificationService;
import net.cis.service.VerifyUserService;

/**
 * 
 * @author liemnh
 *
 */
@Service
public class VerifyUserServiceImpl implements VerifyUserService {
	protected final Logger LOGGER = Logger.getLogger(getClass());

	@Autowired
	private PushNotificationService pushNotificationService;

	@Autowired
	private EmailService emailService;

	@Override
	public void verifyEmail() {
		try {
			emailService.sendEmailActive("test", "liem.nguyen@cis.net.vn");
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}
	}

	@Override
	public void pushNotification() {
		try {
			pushNotificationService.sendAppBroadcastNotitication("", "", "", 0l);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}

	}

}

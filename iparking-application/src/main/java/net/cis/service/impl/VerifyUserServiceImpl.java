package net.cis.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cis.common.util.constant.URLConstants;
import net.cis.jpa.entity.CustomerEmailVerifyEntity;
import net.cis.repository.CustomerEmailVerifyRepository;
import net.cis.service.PushNotificationService;
import net.cis.service.VerifyUserService;
import net.cis.utils.RestfulUtil;

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
	private CustomerEmailVerifyRepository customerEmailVerifyRepository;

	@Override
	public void verifyEmail() {
		try {
			List<CustomerEmailVerifyEntity> lstCustomerEmailVerify = customerEmailVerifyRepository.findAll();
			LOGGER.info("Size" + lstCustomerEmailVerify.size());
			if (lstCustomerEmailVerify != null && lstCustomerEmailVerify.size() > 0) {
				for (CustomerEmailVerifyEntity objCustomerEmailVerifyEntity : lstCustomerEmailVerify) {
					String finalURL = URLConstants.DELEGATE_RESEND_EMAIL_ACTIVATION;
					finalURL = finalURL.replace("{cus_id}", objCustomerEmailVerifyEntity.getCusId());
					finalURL = finalURL.replace("{cus_phone}", objCustomerEmailVerifyEntity.getPhone2());
					String dataResult = RestfulUtil.getWithOutAccessToke(finalURL, null);
					LOGGER.info("Result send email active:" + dataResult);
				}
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}
	}

	@Override
	public void pushNotification() {
		try {
			pushNotificationService.sendInAppMessage("", "", "");
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}

	}


}

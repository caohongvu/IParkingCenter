package net.cis.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cis.common.util.Utils;
import net.cis.jpa.entity.CustomerEmailVerifyEntity;
import net.cis.repository.CustomerEmailVerifyRepository;
import net.cis.service.EmailService;
import net.cis.service.PushNotificationService;
import net.cis.service.VerifyUserService;
import net.cis.utils.ParkingCenterConstants;

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

	@Autowired
	private CustomerEmailVerifyRepository customerEmailVerifyRepository;

	@Override
	public void verifyEmail() {
		try {
			List<CustomerEmailVerifyEntity> lstCustomerEmailVerify = customerEmailVerifyRepository.findAll();
			LOGGER.info("Size" + lstCustomerEmailVerify.size());
			if (lstCustomerEmailVerify != null && lstCustomerEmailVerify.size() > 0) {
				for (CustomerEmailVerifyEntity objCustomerEmailVerifyEntity : lstCustomerEmailVerify) {
					String verifyCode = objCustomerEmailVerifyEntity.getVerificationCode();
					if (StringUtils.isEmpty(verifyCode)) {
						// thuc hien tao verify code
						verifyCode = Utils.createRandomString(20);
					}
					emailService.sendEmailActive(
							buildUrlActive(objCustomerEmailVerifyEntity.getCusId(),
									objCustomerEmailVerifyEntity.getPhone2(), verifyCode),
							objCustomerEmailVerifyEntity.getEmail());
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

	private String buildUrlActive(String cusId, String cusPhone, String verifyCode) {
		StringBuilder strBuilderUrl = new StringBuilder(ParkingCenterConstants.URL_ACTIVE);
		strBuilderUrl.append("cus_id=").append(cusId);
		strBuilderUrl.append("?cus_phone=").append(cusPhone);
		strBuilderUrl.append("?h=").append(verifyCode);
		return strBuilderUrl.toString();
	}

}

package net.cis.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import net.cis.common.util.DateTimeUtil;
import net.cis.common.util.constant.URLConstants;
import net.cis.common.util.constant.UserConstant;
import net.cis.constants.CustomerConstans;
import net.cis.constants.NotificationTypeEnum;
import net.cis.dto.NotificationCustomerDto;
import net.cis.dto.NotificationData;
import net.cis.dto.NotificationDto;
import net.cis.jpa.entity.CustomerEmailVerifyViewEntity;
import net.cis.jpa.entity.CustomerNonInfoViewEntity;
import net.cis.repository.CustomerEmailVerifyRepository;
import net.cis.repository.CustomerNonInfoRepository;
import net.cis.service.NotificationService;
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

	@Autowired
	private CustomerNonInfoRepository customerNonInfoRepository;

	@Autowired
	NotificationService notificationService;

	/**
	 * thuc hien gui email|| thuc hien gui den nhung customer chua verify email
	 */
	@Override
	public void verifyEmail() {
		try {
			List<CustomerEmailVerifyViewEntity> lstCustomerEmailVerify = customerEmailVerifyRepository.findAll();
			LOGGER.info("CustomerEmailVerify Size" + lstCustomerEmailVerify.size());
			if (lstCustomerEmailVerify != null && lstCustomerEmailVerify.size() > 0) {
				for (CustomerEmailVerifyViewEntity objCustomerEmailVerifyEntity : lstCustomerEmailVerify) {
					if (!String.valueOf(UserConstant.STATUS_ACTIVATED).equals(objCustomerEmailVerifyEntity.getStatus())
							&& !StringUtils.isEmpty(objCustomerEmailVerifyEntity.getVerificationCode())) {
						sendEmailActive(objCustomerEmailVerifyEntity.getCusId(),
								objCustomerEmailVerifyEntity.getPhone2());
					}
				}
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}
	}

	/**
	 * thuc hien push notification den customer chua co customer info va co
	 * customer info nhưng chua verify
	 */
	@Override
	public void pushNotification() {
		try {
			List<String> lstPlayerIds = new ArrayList<>();
			List<CustomerEmailVerifyViewEntity> lstCustomerEmailVerify = customerEmailVerifyRepository.findAll();
			LOGGER.info("pushNotification CustomerEmailVerifyViewEntity Size" + lstCustomerEmailVerify.size());
			for (CustomerEmailVerifyViewEntity obj : lstCustomerEmailVerify) {
				lstPlayerIds.add(obj.getToken().split(";")[0]);
			}
			List<CustomerNonInfoViewEntity> lstCustomerNonInfoViewEntity = customerNonInfoRepository.findAll();
			LOGGER.info("pushNotification CustomerNonInfo Size" + lstCustomerNonInfoViewEntity.size());
			// thuc hien gui thông tin den cho app user

			String title = "Thông tin tài khoản";
			String content = "Đề nghị KH cập nhật thông tin tài khoản iParking";
			// thuc hien insert DB (notification )
			NotificationDto objNotificationHistoryDto = new NotificationDto();
			objNotificationHistoryDto.setTitle(title);
			objNotificationHistoryDto.setContent(content);
			objNotificationHistoryDto.setCreatedAt(DateTimeUtil.getCurrentDateTime());
			NotificationData notificationData = new NotificationData();
			notificationData.setType(NotificationTypeEnum.VERIFY_EMAIL);
			Gson gson = new Gson();
			objNotificationHistoryDto.setData(gson.toJson(notificationData));
			objNotificationHistoryDto = notificationService.saveNotification(objNotificationHistoryDto);

			for (CustomerNonInfoViewEntity obj : lstCustomerNonInfoViewEntity) {
				lstPlayerIds.add(obj.getToken().split(";")[0]);
				// thuc hien them moi noti_customer
				NotificationCustomerDto objNotificationCustomerDto = new NotificationCustomerDto();
				objNotificationCustomerDto.setNotificationId(objNotificationHistoryDto.getId());
				objNotificationCustomerDto.setCusId(Long.parseLong(obj.getCusId()));
				objNotificationCustomerDto.setCreatedAt(DateTimeUtil.getCurrentDateTime());
				objNotificationCustomerDto.setIsRead(CustomerConstans.CUSTOMER_NOTIFICATION_UN_READ);
				notificationService.saveNotificationCustomer(objNotificationCustomerDto);
			}
			if (lstPlayerIds.size() > 0) {
				pushNotificationService.sendNotificationForPlayerIds(lstPlayerIds, NotificationTypeEnum.VERIFY_EMAIL,
						title, content);
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}
	}

	private void sendEmailActive(String cusId, String phone) {
		try {
			String finalURL = URLConstants.DELEGATE_RESEND_EMAIL_ACTIVATION;
			finalURL = finalURL.replace("{cus_id}", cusId);
			finalURL = finalURL.replace("{cus_phone}", phone);
			String dataResult = RestfulUtil.getWithOutAccessToke(finalURL, null);
			LOGGER.info("Result send email active:" + dataResult);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}
	}

}

package net.cis.service.impl;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import net.cis.common.util.DateTimeUtil;
import net.cis.common.util.constant.URLConstants;
import net.cis.common.util.constant.UserConstant;
import net.cis.dto.CustomerInfoDto;
import net.cis.service.CustomerService;
import net.cis.service.EmailService;
import net.cis.utils.RestfulUtil;

/**
 * Created by liemnh
 */
@Service
public class EmailServiceImpl implements EmailService {

	protected final Logger LOGGER = Logger.getLogger(getClass());
	@Autowired
	JavaMailSender mailSender;

	@Autowired
	CustomerService customerService;

	@Override
	public void send(String title, String content) {
		mailSender.send(new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				messageHelper.setFrom("automailer@cis.net.vn");
				messageHelper.setTo("operation_iparking@cis.net.vn");
				messageHelper.setSubject(title);
				messageHelper.setText(content, true);
			}
		});
	}

	public static int noOfQuickServiceThreads = 100;
	private ScheduledExecutorService quickService = Executors.newScheduledThreadPool(noOfQuickServiceThreads);

	public void sendASynchronousMail(String title, String content) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom("automailer@cis.net.vn");
		mail.setTo("operation_iparking@cis.net.vn");
		mail.setSubject(title);
		mail.setText(content);
		quickService.submit(new Runnable() {
			@Override
			public void run() {
				try {
					mailSender.send(mail);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void checkAndsendMailActiveASynchronous(long customer, String phone, String email) throws Exception {
		quickService.submit(new Runnable() {
			@Override
			public void run() {
				try {
					CustomerInfoDto objCustomerInfoDto = customerService.findCustomerInfoByCusId(customer);
					if (objCustomerInfoDto == null) {
						// thuc hien tao CustomerInfo
						objCustomerInfoDto = new CustomerInfoDto();
						objCustomerInfoDto.setCusId(customer);
						objCustomerInfoDto.setEmail(email);
						objCustomerInfoDto.setVerificationCode("");
						objCustomerInfoDto.setStatus(UserConstant.STATUS_NOT_VERIFIED);
						objCustomerInfoDto.setCreatedAt(DateTimeUtil.getCurrentDateTime());
						customerService.saveCustomerInfoEntity(objCustomerInfoDto);
						customerService.saveCustomerInfoInPoseidonDb(customer, phone, email);
					} else if (objCustomerInfoDto != null && !email.equalsIgnoreCase(objCustomerInfoDto.getEmail())) {
						objCustomerInfoDto.setEmail(email);
						objCustomerInfoDto.setStatus(UserConstant.STATUS_NOT_VERIFIED);
						customerService.saveCustomerInfoEntity(objCustomerInfoDto);
						customerService.saveCustomerInfoInPoseidonDb(customer, phone, email);
					} else if (objCustomerInfoDto != null && email.equalsIgnoreCase(objCustomerInfoDto.getEmail())
							&& (UserConstant.STATUS_NOT_VERIFIED == objCustomerInfoDto.getStatus()
									|| (UserConstant.STATUS_NOT_VERIFIED == objCustomerInfoDto.getStatus()
											&& StringUtils.isEmpty(objCustomerInfoDto.getVerificationCode())))) {
						reSendMailActive(customer, phone);
					}
				} catch (Exception e) {
					LOGGER.error("Lỗi gửi email active: " + e.getMessage());
				}
			}
		});
	}

	private void reSendMailActive(long customer, String phone) {
		// thuc hien gui mail
		String finalURL = URLConstants.DELEGATE_RESEND_EMAIL_ACTIVATION;
		finalURL = finalURL.replace("{cus_id}", String.valueOf(customer));
		finalURL = finalURL.replace("{cus_phone}", phone);
		String dataResult = RestfulUtil.getWithOutAccessToke(finalURL, null);
		LOGGER.info("Result send email active:" + dataResult);
	}

	@Override
	public void sendASynchronousMail(String title, String content, String[] emailTos) throws Exception {
//		SimpleMailMessage mail = new SimpleMailMessage();
//		mail.setFrom("automailer@cis.net.vn");
//		mail.setTo("operation_iparking@cis.net.vn");
//		mail.setBcc(emailTos);
//		mail.setSubject(title);
//		mail.setText(content);
//		quickService.submit(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					mailSender.send(mail);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});

	}
}

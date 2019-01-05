package net.cis.service.impl;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import net.cis.service.EmailService;

/**
 * Created by liemnh
 */
@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	JavaMailSender mailSender;

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
}

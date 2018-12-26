package net.cis.service.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
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

	public static int noOfQuickServiceThreads = 20;

	private ScheduledExecutorService quickService = Executors.newScheduledThreadPool(noOfQuickServiceThreads);

	public void sendASynchronousMail(String title, String content) throws MailException, RuntimeException {
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
				}
			}
		});
	}

	public void sendEmailActive(String urlActive, String email) {
		BufferedReader br = null;
		try {
			FileReader reader = new FileReader(new ClassPathResource("email_verify.html").getFile());
			StringBuilder sb = new StringBuilder();
			br = new BufferedReader(reader);
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			MimeMessage message = mailSender.createMimeMessage();
			message.setContent(sb.toString().replace("[URL_ACTIVE]", urlActive), "text/html; charset=utf-8");
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setFrom("automailer@cis.net.vn");
			helper.setTo(email);
			helper.setSubject("Xác thực email với iParking");
			quickService.submit(new Runnable() {
				@Override
				public void run() {
					try {
						mailSender.send(message);
					} catch (Exception e) {
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}

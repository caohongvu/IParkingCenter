package net.cis.service;

/**
 * Created by liemnh
 */
public interface EmailService {

	public void send(String title, String content) throws Exception;

	public void sendASynchronousMail(String title, String content) throws Exception;

	public void sendASynchronousMail(String title, String content, String[] emailTos) throws Exception;

	public void checkAndsendMailActiveASynchronous(long customer, String phone, String email) throws Exception;

	public void sendEmailResendPassword(String title, String content, String emailTo) throws Exception;

	public void sendEmailChangePasswordSuccess(String title, String content, String emailTo) throws Exception;

	public void sendMailCheckWaringNoRevenue(String title, String content) throws Exception;

}

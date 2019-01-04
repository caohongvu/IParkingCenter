package net.cis.service;

/**
 * Created by liemnh
 */
public interface EmailService {

	public void send(String title, String content) throws Exception;

	public void sendASynchronousMail(String title, String content) throws Exception;

}

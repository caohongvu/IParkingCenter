package net.cis.service;

public interface SmsService {
	boolean sendSms(String phone, String content) throws Exception;
}

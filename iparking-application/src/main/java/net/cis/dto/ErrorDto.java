package net.cis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorDto {	
	private int Code;
	private String Message;
	@JsonProperty("Code")
	public int getCode() {
		return Code;
	}
	public void setCode(int code) {
		Code = code;
	}
	@JsonProperty("Message")
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public ErrorDto() {
		
	}
	public ErrorDto(int code, String message) {
		Code = code;
		Message = message;
	}
	
	
	
}

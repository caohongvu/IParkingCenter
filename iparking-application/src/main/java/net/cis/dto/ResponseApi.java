package net.cis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseApi {
	private Object Data;
	private ErrorDto Error;
	@JsonProperty("Data")
	public Object getData() {
		return Data;
	}
	public void setData(Object data) {
		Data = data;
	}
	@JsonProperty("Error")
	public ErrorDto getError() {
		return Error;
	}
	public void setError(ErrorDto error) {
		Error = error;
	}
	
}

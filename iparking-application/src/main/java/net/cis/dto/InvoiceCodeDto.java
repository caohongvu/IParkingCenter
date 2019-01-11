package net.cis.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by NhanNguyen
 */
public class InvoiceCodeDto {

	@JsonProperty("Codes")
	private List<String> codes;
	
	@JsonProperty("Url")
	private String url;

	public List<String> getCodes() {
		return codes;
	}

	public void setInvoiceCodes(List<String> codes) {
		this.codes = codes;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}

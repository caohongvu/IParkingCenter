package net.cis.dto;

import java.util.List;

public class TicketDailyPortalEndPointDto {
	private long total_row;
	private long countCpp_code;
	private long countNumber_plate;
	private long countPhone;
	
	private List<TicketDailyPortalDto> ticketDaily;

	public long getTotal_row() {
		return total_row;
	}

	public void setTotal_row(long total_row) {
		this.total_row = total_row;
	}

	public long getCountCpp_code() {
		return countCpp_code;
	}

	public void setCountCpp_code(long countCpp_code) {
		this.countCpp_code = countCpp_code;
	}

	public long getCountNumber_plate() {
		return countNumber_plate;
	}

	public void setCountNumber_plate(long countNumber_plate) {
		this.countNumber_plate = countNumber_plate;
	}

	public long getCountPhone() {
		return countPhone;
	}

	public void setCountPhone(long countPhone) {
		this.countPhone = countPhone;
	}

	public List<TicketDailyPortalDto> getTicketDaily() {
		return ticketDaily;
	}

	public void setTicketDaily(List<TicketDailyPortalDto> ticketDaily) {
		this.ticketDaily = ticketDaily;
	}
	
	
	

}

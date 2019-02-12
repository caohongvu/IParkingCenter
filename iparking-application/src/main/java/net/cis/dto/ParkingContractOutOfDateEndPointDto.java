package net.cis.dto;

import java.util.List;

public class ParkingContractOutOfDateEndPointDto {
	private long total_row;
	private List<ParkingContractOutOfDateDto> parkingContractOutOfDateDtos;
	public long getTotal_row() {
		return total_row;
	}
	public void setTotal_row(long total_row) {
		this.total_row = total_row;
	}
	public List<ParkingContractOutOfDateDto> getParkingContractOutOfDateDtos() {
		return parkingContractOutOfDateDtos;
	}
	public void setParkingContractOutOfDateDtos(List<ParkingContractOutOfDateDto> parkingContractOutOfDateDtos) {
		this.parkingContractOutOfDateDtos = parkingContractOutOfDateDtos;
	}
	
}

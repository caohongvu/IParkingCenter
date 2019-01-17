package net.cis.service;

import java.util.List;

import net.cis.dto.ReportDailyPaymentDto;

public interface ReportDailyPaymentServic {
	List<ReportDailyPaymentDto> getSummaryReportDailyPaymentByParkingPlace(List<Long> cppId, Long fromDate, Long toDate);
}

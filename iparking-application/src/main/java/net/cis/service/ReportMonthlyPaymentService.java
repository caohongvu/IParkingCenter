package net.cis.service;

import java.util.List;

import net.cis.dto.ReportMonthlyPaymentDto;

public interface ReportMonthlyPaymentService {
	List<ReportMonthlyPaymentDto> getSummaryReportMonthlyPaymentByParkingPlace(List<Long> cppId, Long fromDate, Long toDate);
}

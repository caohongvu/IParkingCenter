package net.cis.service;

import java.util.List;

import net.cis.dto.ReportProportionPaymentDto;

public interface ReportProportionPaymentService {
	List<ReportProportionPaymentDto> getProportionPayment(List<Long> cppId, long fromDate, long toDate);

	ReportProportionPaymentDto getProportionPayment(long cppId, long fromDate, long toDate);
}

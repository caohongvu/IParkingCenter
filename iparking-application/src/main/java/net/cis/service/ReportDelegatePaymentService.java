package net.cis.service;

import java.util.List;

import net.cis.dto.ProportionPaymentDto;
import net.cis.dto.ReportDelegatePaymentDto;

public interface ReportDelegatePaymentService {

	List<ReportDelegatePaymentDto> findByCarppIdsAndDate(List<Long> carppIds, String date);

	List<ProportionPaymentDto> getProportionPayment(List<Long> cppId, String fromDate, String toDate);

	ProportionPaymentDto getProportionPayment(long cppId, String fromDate, String toDate);

}

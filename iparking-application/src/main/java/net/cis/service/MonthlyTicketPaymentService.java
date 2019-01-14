package net.cis.service;

import org.springframework.data.domain.Pageable;

import net.cis.dto.ResponseApi;
import net.cis.jpa.criteria.MonthlyTicketPaymentCriteria;

public interface MonthlyTicketPaymentService {

	ResponseApi findAll(MonthlyTicketPaymentCriteria monthlyTicketPaymentCriteria, Pageable pageable);

	ResponseApi getRevenueGroupByParkingCodeSP(MonthlyTicketPaymentCriteria monthlyTicketPaymentCriteria);
	
	ResponseApi getRevenueGroupByCompanyCodeSP(MonthlyTicketPaymentCriteria monthlyTicketPaymentCriteria);

}

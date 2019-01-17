package net.cis.service;

import org.springframework.data.domain.Pageable;

import net.cis.dto.ResponseApi;
import net.cis.jpa.criteria.MonthlyTicketReportCriteria;

public interface MonthlyTicketReportService {
	ResponseApi findAll(MonthlyTicketReportCriteria monthlyTicketReportCriteria,Pageable pageable);
}

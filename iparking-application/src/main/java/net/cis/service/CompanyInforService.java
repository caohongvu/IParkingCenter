package net.cis.service;

import java.util.List;
import net.cis.jpa.entity.CompanyInforEntity;

public interface CompanyInforService {
	List<CompanyInforEntity> findCompanyNeedMonthlyInvoice();
}

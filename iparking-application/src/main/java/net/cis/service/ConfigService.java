package net.cis.service;

import net.cis.dto.CompanyServiceDto;
import net.cis.dto.ResponseApi;
import net.cis.jpa.entity.CompanyServiceEntity;

public interface ConfigService {
	 ResponseApi findAllConfigOfCompany(int id);
	 
	 ResponseApi findCompanyNeedMonthlyInvoice();
	 
	 CompanyServiceEntity save(CompanyServiceEntity companyServiceentity);
	 
	 ResponseApi configCompanyService (CompanyServiceDto companyServiceentity, int companyId);
	 
}

package net.cis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.cis.jpa.entity.CompanyInforEntity;
import net.cis.repository.CompanyInforRepository;
import net.cis.service.CompanyInforService;
import net.cis.utils.ParkingCenterConstants;

/**
 * Created by NhanNguyen
 */
@Service
public class CompanyInforServiceImpl implements CompanyInforService {
	
	@Autowired
	CompanyInforRepository companyInforRepository;
	
	@Override
	public List<CompanyInforEntity> findCompanyNeedMonthlyInvoice() {
		List<CompanyInforEntity> companies = companyInforRepository.findByMonthlyInvoice(ParkingCenterConstants.MONTHLY_INVOICE);
		
		return companies;
	}

	

}

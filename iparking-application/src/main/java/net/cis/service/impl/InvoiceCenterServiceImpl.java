package net.cis.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.cis.dto.CompanyDto;
import net.cis.dto.CustomerDto;
import net.cis.dto.CustomerInfoDto;
import net.cis.dto.MonthlyInvoiceDto;
import net.cis.dto.ParkingDto;
import net.cis.dto.PaymentConfigDto;
import net.cis.jpa.entity.CompanyInforEntity;
import net.cis.jpa.entity.ParkingContractEntity;
import net.cis.jpa.entity.ParkingEntity;
import net.cis.service.CompanyInforService;
import net.cis.service.CompanyService;
import net.cis.service.CustomerService;
import net.cis.service.InvoiceCenterService;
import net.cis.service.ParkingContractService;
import net.cis.service.ParkingService;
import net.cis.utils.InvoiceCenterConstants;
import net.cis.utils.RestfulUtil;

/**
 * Created by NhanNguyen
 */
@Service
public class InvoiceCenterServiceImpl implements InvoiceCenterService {

	@Autowired
	CompanyInforService companyInforService;
	
	@Autowired
	ParkingService parkingService;
	
	@Autowired
	ParkingContractService parkingContractService;
	
	@Autowired
	CustomerService customerService;

	@Autowired
	CompanyService companyService;
	
	@Override
	public List<String> getInvoiceCode(Long ticketId) throws Exception {
		List<String> codes = new ArrayList<String>();
		
		String response = RestfulUtil.get(InvoiceCenterConstants.GET_INVOICE_CODE_FOR_MONTHLY + ticketId, "application/json");
		JSONObject json = new JSONObject(response);
		JSONArray data = json.getJSONArray("Data");
		
		for (int i = 0; i < data.length(); i++) {
			codes.add(data.getString(i));
		}
		
		return codes;
	}
	
	@Scheduled(fixedDelay=5*60*1000)
	private void sendMonthlyInvoice() throws Exception {
		// Get List of Company
		List<CompanyInforEntity> entities = companyInforService.findCompanyNeedMonthlyInvoice();
		
		for (CompanyInforEntity entity : entities) {
			// Get List Parking Place of company
			List<ParkingEntity> parkingEntities = parkingService.findByCompanyId(entity.getCompanyId());
			
			// Get List ticket of Parking Place
			for (ParkingEntity parkingEntity : parkingEntities) {
				List<ParkingContractEntity> parkingContractEntities = parkingContractService.findByParkingPlace(parkingEntity.getParkingCode());
				
				// Send Month Invoice for Parking Contract
				for (ParkingContractEntity parkingContractEntity : parkingContractEntities) {
					MonthlyInvoiceDto monthlyInvoiceDto = initializeMonthInvoiceData(parkingContractEntity);
					
					ObjectMapper mapper = new ObjectMapper();
					String requestStr = mapper.writeValueAsString(monthlyInvoiceDto);
					JSONObject requestObject = new JSONObject(monthlyInvoiceDto);
					System.out.println(requestStr);
					System.out.println(requestObject);
					
					// Trigger INVOICE CENTER to create Monthly Invoice
					String responseStr = RestfulUtil.post(InvoiceCenterConstants.CREATE_MONTHLY_INVOICE, requestObject, InvoiceCenterConstants.INVOICE_CENTER_MEDIA_TYPE);
					JSONObject responseObject = new JSONObject(responseStr);
					
					System.out.println(responseObject);
					
				}
				
			}
			
		}
	}
	
	private MonthlyInvoiceDto initializeMonthInvoiceData(ParkingContractEntity entity) throws Exception {
		MonthlyInvoiceDto dto = new MonthlyInvoiceDto();
		Calendar now = Calendar.getInstance();
		Integer month = now.get(Calendar.MONTH + 1);
		Integer year = now.get(Calendar.YEAR);
		
		String buyerName = "";
		String receiverEmail = "";

		CompanyDto companyDto = companyService.findByCompanyCode(entity.getCompany());
		if (companyDto != null) {
			dto.setProviderId(companyDto.getId());
		}
		
		ParkingDto parkingDto = parkingService.findByParkingCode(entity.getParkingPlace());
		if (parkingDto != null) {
			dto.setCppId(parkingDto.getOldId());
		}
		
		CustomerDto customerDto = customerService.findCustomerByOldId(entity.getCusId());
		if (customerDto != null) {
			dto.setReceiverMobile(customerDto.getPhone());
			buyerName = customerDto.getPhone();
			receiverEmail = "";
		}
		
		CustomerInfoDto customerInfoDto = customerService.findCustomerInfoByCusId(entity.getCusId());
		if (customerInfoDto != null) {
			buyerName = customerInfoDto.getEmail();
			receiverEmail = customerInfoDto.getEmail();
		}
		
		dto.setBuyerName(buyerName);
		dto.setReceiverEmail(receiverEmail);
		dto.setReceiverName("");
		dto.setReceiverMobile(customerDto.getPhone2());
		dto.setBuyerTaxCode("");
		dto.setBuyerUnitName("");
		dto.setBuyerAddress("");
		dto.setBuyerBankAccount("");
		dto.setReceiverAddress("");
		dto.setTicketId(entity.getId());
		dto.setCppCode(entity.getParkingPlace());
		dto.setTransactionId(dto.getTicketId() + "_" + month + "_" + year);
		dto.setTransactionAmount(entity.getMonthlyUnitPrice());
		dto.setIsMonthly(1);
		
		List<PaymentConfigDto> list = new ArrayList<>();
		
		PaymentConfigDto paymentConfigDto = new PaymentConfigDto();
		String itemName = "Dịch vụ trông giữ ô tô tại điểm đỗ {0} tháng {1} năm {2}";
		itemName = itemName.replace("{0}", entity.getParkingPlace());
		itemName = itemName.replace("{1}", month.toString());
		itemName = itemName.replace("{2}", year.toString());
		paymentConfigDto.setItemName(itemName);
		paymentConfigDto.setPrice(entity.getMonthlyUnitPrice());
		list.add(paymentConfigDto);
		
		dto.setPaymentConfiguration(list);
		
		
		return dto;
	}

	
}

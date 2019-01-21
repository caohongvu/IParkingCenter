package net.cis.service.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cis.constants.ResponseErrorCodeConstants;
import net.cis.dto.CompanyServiceDto;
import net.cis.dto.ErrorDto;
import net.cis.dto.ResponseApi;
import net.cis.jpa.entity.CompanyServiceEntity;
import net.cis.repository.CompanyServiceReponsitory;
import net.cis.service.ConfigService;
import net.cis.utils.ParkingCenterConstants;

@Service
public class ConfigServiceImpl implements ConfigService {
	ModelMapper mapper;

	@Autowired
	private CompanyServiceReponsitory companyServiceReponsitory;

	@Override
	public ResponseApi findAllConfigOfCompany(int id) {
		ErrorDto errorDto = new ErrorDto();
		ResponseApi responseApi = new ResponseApi();
		ModelMapper mapper = new ModelMapper();

		List<CompanyServiceEntity> entity = companyServiceReponsitory.findConfigByCompanyId(id);
		if (entity == null) {
			errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
			errorDto.setMessage("");
			responseApi.setError(errorDto);
			responseApi.setData(null);

			return responseApi;
		}
		CompanyServiceDto companyServiceDto = new CompanyServiceDto();

		for (int i = 0; i < entity.size(); i++) {
			CompanyServiceEntity object = entity.get(i);
			if (object.getServiceId() == 1) {
				companyServiceDto.setApply_einvoice(1);
			}
			if (object.getServiceId() == 2) {
				companyServiceDto.setEnable_viettel_pay(1);
			}
			if (object.getServiceId() == 3) {
				companyServiceDto.setUse_parking_center(1);
			}
			if (object.getServiceId() == 4) {
				companyServiceDto.setUse_napas_3(1);
			}
		}
		// CompanyServiceDto companyServiceDto = new CompanyServiceDto();
		// mapper.map(entity, companyServiceDto);

		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		errorDto.setMessage("");
		responseApi.setError(errorDto);
		responseApi.setData(companyServiceDto);
		return responseApi;
	}

	@Override
	public ResponseApi findCompanyNeedMonthlyInvoice() {
		ResponseApi responseApi = new ResponseApi();
		List<CompanyServiceEntity> companies = companyServiceReponsitory
				.findByServiceId(ParkingCenterConstants.MONTHLY_INVOICE);

		responseApi.setData(companies);
		responseApi.setError(new ErrorDto(ResponseErrorCodeConstants.StatusOK, ""));

		return responseApi;
	}

	@Override
	public CompanyServiceEntity save(CompanyServiceEntity companyServiceDto) {
		CompanyServiceEntity companyDto = companyServiceReponsitory.save(companyServiceDto);
		return companyDto;
	}

	@Override
	public ResponseApi configCompanyService(CompanyServiceDto companyServiceDto, int companyId) {

		List<CompanyServiceEntity> listServicesOfCompany = companyServiceReponsitory.findConfigByCompanyId(companyId);

		// list services will config
		HashSet<Integer> listServicesWillCongif = new HashSet<Integer>();
		if (companyServiceDto.getApply_einvoice() == 1) {
			listServicesWillCongif.add(1);
		}
		if (companyServiceDto.getEnable_viettel_pay() == 1) {
			listServicesWillCongif.add(2);
		}
		if (companyServiceDto.getUse_parking_center() == 1) {
			listServicesWillCongif.add(3);
		}
		if (companyServiceDto.getUse_napas_3() == 1) {
			listServicesWillCongif.add(4);
		}

		// list current Services of company
		HashSet<Integer> listServicesCurrent = new HashSet<Integer>();
		for (int i = 0; i < listServicesOfCompany.size(); i++) {
			if (listServicesOfCompany.get(i).getServiceId() == 1) {
				listServicesCurrent.add(1);
			}
			if (listServicesOfCompany.get(i).getServiceId() == 2) {
				listServicesCurrent.add(2);
			}
			if (listServicesOfCompany.get(i).getServiceId() == 3) {
				listServicesCurrent.add(3);
			}
			if (listServicesOfCompany.get(i).getServiceId() == 4) {
				listServicesCurrent.add(4);
			}

		}

		HashSet<Integer> listAll = new HashSet<Integer>();

		Iterator<Integer> configInt = listServicesWillCongif.iterator();
		while (configInt.hasNext()) {
			listAll.add(configInt.next());
		}

		Iterator<Integer> currentInt = listServicesCurrent.iterator();
		while (currentInt.hasNext()) {
			listAll.add(currentInt.next());
		}

		HashSet<Integer> listServicesAdd = new HashSet<Integer>();
		HashSet<Integer> listServicesDelete = new HashSet<Integer>();

		Iterator<Integer> allInt = listAll.iterator();
		while (allInt.hasNext()) {
			int item = allInt.next();
			if (!listServicesCurrent.contains(item) && listServicesWillCongif.contains(item)) {
				listServicesAdd.add(item);
			}

			if (listServicesCurrent.contains(item) && !listServicesWillCongif.contains(item)) {
				listServicesDelete.add(item);
			}

		}
		System.out.println(listServicesAdd);
		System.out.println(listServicesDelete);
		// delete
		if (listServicesDelete != null) {
			Iterator<Integer> deleteInt = listServicesDelete.iterator();
			while (deleteInt.hasNext()) {
				int serviceId = deleteInt.next();
				for (int i = 0; i < listServicesOfCompany.size(); i++) {
					if (listServicesOfCompany.get(i).getServiceId() == serviceId) {
						companyServiceReponsitory.delete(listServicesOfCompany.get(i).getId());

					}
				}

			}
		}
		// add
		if (listServicesAdd != null) {
			Iterator<Integer> addInt = listServicesAdd.iterator();
			while (addInt.hasNext()) {
				CompanyServiceEntity entity = new CompanyServiceEntity();
				entity.setCompanyId(companyId);
				entity.setServiceId(addInt.next());
				companyServiceReponsitory.save(entity);

			}
		}
		ErrorDto errorDto = new ErrorDto();
		ResponseApi responseApi = new ResponseApi();
		errorDto.setCode(HttpStatus.SC_OK);
		errorDto.setMessage("");
		responseApi.setData(null);
		responseApi.setError(errorDto);
		return responseApi;
	}

}

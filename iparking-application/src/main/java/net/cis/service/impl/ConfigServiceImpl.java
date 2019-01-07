package net.cis.service.impl;

import java.util.List;

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

@Service
public class ConfigServiceImpl implements ConfigService{
	@Autowired
	private CompanyServiceReponsitory companyServiceReponsitory;

	@Override
	public ResponseApi findAllConfigOfCompany(int id) {
		ErrorDto errorDto = new ErrorDto();
		ResponseApi responseApi = new ResponseApi();
		ModelMapper mapper = new ModelMapper();
		
		List<CompanyServiceEntity> entity = companyServiceReponsitory.findConfigByCompanyId(id);
		if(entity == null) {
			errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
			errorDto.setMessage("");
			responseApi.setError(errorDto);
			responseApi.setData(null);

			return responseApi;
		}
		CompanyServiceDto companyServiceDto = new CompanyServiceDto();
		
		for (int i = 0; i< entity.size(); i++) {
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
//		CompanyServiceDto companyServiceDto = new CompanyServiceDto();
//		mapper.map(entity, companyServiceDto);
		
		errorDto.setCode(ResponseErrorCodeConstants.StatusOK);
		errorDto.setMessage("");
		responseApi.setError(errorDto);
		responseApi.setData(companyServiceDto);
		return responseApi;
	}

}

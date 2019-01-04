package net.cis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.cis.common.util.Utils;
import net.cis.common.util.constant.UserConstant;
import net.cis.dto.CustomerDto;
import net.cis.dto.ResponseDto;
import net.cis.jpa.entity.CustomerCarEntity;
import net.cis.jpa.entity.CustomerInfoEntity;
import net.cis.repository.CustomerCarRepository;
import net.cis.repository.CustomerInfoRepository;

/**
 * 
 * @author liemnh
 *
 */
@RestController
@RequestMapping("/customer")
@Api(value = "customer Endpoint", description = "The URL to handle customer endpoint")
public class CustomerEndpoint {
	@Autowired
	CustomerCarRepository customerCarRepository;
	@Autowired
	CustomerInfoRepository customerInfoRepository;

	@RequestMapping(value = "/find-by-numberplate/", method = RequestMethod.GET)
	@ApiOperation("Fetch details of ticket")
	public @ResponseBody ResponseDto getById(@RequestParam(name = "numberPlate") String numberPlate) throws Exception {
		ResponseDto responseDto = new ResponseDto();
		responseDto.setCode(HttpStatus.OK.toString());
		if (!Utils.validateNumberPlate(numberPlate)) {
			responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
			responseDto.setMessage("Biển số xe sai định dạng");
			return responseDto;
		}

		// thuc hien lay thong tin customer tu bien so xe
		List<CustomerCarEntity> lstCustomerCarEntity = customerCarRepository.findCustomerCarByNumberPlate(numberPlate,
				UserConstant.STATUS_VERIFIED);
		if (lstCustomerCarEntity != null && lstCustomerCarEntity.size() == 1) {
			CustomerDto objCustomerDto = new CustomerDto();
			CustomerCarEntity objCustomerCarEntity = lstCustomerCarEntity.get(0);
			if (objCustomerCarEntity.getCustomer() != null) {
				objCustomerDto.setId(objCustomerCarEntity.getCustomer().getOldId());
				objCustomerDto.setPhone2(objCustomerCarEntity.getCustomer().getPhone2());
				objCustomerDto.setPhone(objCustomerCarEntity.getCustomer().getPhone());
				objCustomerDto.setStatus(objCustomerCarEntity.getCustomer().getStatus());
				// tim kiem thong tin email
				CustomerInfoEntity objCustomerInfoEntity = customerInfoRepository
						.findByCusId(objCustomerCarEntity.getCustomer().getOldId());
				if (objCustomerInfoEntity != null) {
					objCustomerDto.setEmail(objCustomerInfoEntity.getEmail());
				}
			}
			objCustomerDto.setNumberPlate(objCustomerCarEntity.getNumberPlate());

			responseDto.setData(objCustomerDto);
			return responseDto;
		}
		return responseDto;
	}

}

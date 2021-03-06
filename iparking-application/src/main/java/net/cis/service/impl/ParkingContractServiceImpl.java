package net.cis.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.cis.dto.CompanyDto;
import net.cis.dto.InvoiceCodeDto;
import net.cis.dto.ParkingContractDto;
import net.cis.dto.ParkingContractInfoDto;
import net.cis.dto.ParkingContractOutOfDateDto;
import net.cis.dto.ParkingContractOutOfDateEndPointDto;
import net.cis.dto.ParkingDto;
import net.cis.jpa.criteria.ParkingContractCriteria;
import net.cis.jpa.entity.ParkingContractEntity;
import net.cis.jpa.entity.ParkingContractInfoEntity;
import net.cis.jpa.entity.ParkingContractOutOfDateEntity;
import net.cis.jpa.entity.ParkingContractOutOfDateFooterEntity;
import net.cis.repository.ParkingContractInfoRepository;
import net.cis.repository.ParkingContractOutOfDateFooterRepository;
import net.cis.repository.ParkingContractOutOfDateRepository;
import net.cis.repository.ParkingContractRepository;
import net.cis.service.InvoiceCenterService;
import net.cis.service.ParkingContractService;
import net.cis.service.cache.CompanyCache;
import net.cis.service.cache.ParkingPlaceCache;
import net.cis.utils.InvoiceCenterConstants;

/**
 * Created by Vincent on 02/10/2018
 */
@Service
public class ParkingContractServiceImpl implements ParkingContractService {

	ModelMapper mapper;

	@Autowired
	ParkingContractOutOfDateRepository parkingContractOutOfDateRepository;
	
	@Autowired
	ParkingContractOutOfDateFooterRepository parkingContractOutOfDateFooterRepository;

	@Autowired
	ParkingContractInfoRepository parkingContractInfoRepository;

	@Autowired
	ParkingContractRepository parkingContractRepository;

	@Autowired
	ParkingPlaceCache parkingPlaceCache;

	@Autowired
	InvoiceCenterService invoiceCenterService;

	@Autowired
	private CompanyCache companyCache;

	@Override
	public ParkingContractDto findOne(long id) {
		ParkingContractEntity entity = parkingContractRepository.findOne(id);
		ParkingContractDto dto = new ParkingContractDto();
		mapper.map(entity, dto);

		try {
			List<String> codes = invoiceCenterService.getInvoiceCode(id);
			InvoiceCodeDto invoiceCodeDto = new InvoiceCodeDto();
			invoiceCodeDto.setInvoiceCodes(codes);
			invoiceCodeDto.setUrl(InvoiceCenterConstants.DOWNLOAD_INVOICE_URL);

			dto.setInvoiceCodeDto(invoiceCodeDto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dto;
	}

	@Override
	public List<ParkingContractDto> findByCustomer(Long cusId) {
		List<ParkingContractEntity> entities = parkingContractRepository.findByCusId(cusId);

		return this.map(entities);
	}

	@Override
	public ParkingContractDto save(ParkingContractDto parkingContractDto) {

		ParkingContractEntity entity = new ParkingContractEntity();
		mapper.map(parkingContractDto, entity);
		parkingContractRepository.save(entity);

		mapper.map(entity, parkingContractDto);
		return parkingContractDto;
	}

	@Override
	public ParkingContractDto update(ParkingContractDto parkingContractDto) {

		return save(parkingContractDto);
	}

	public List<ParkingContractDto> map(List<ParkingContractEntity> source) {

		ArrayList<ParkingContractDto> rtn = new ArrayList<>();
		source.stream().map((entity) -> {
			ParkingContractDto dto = new ParkingContractDto();
			mapper.map(entity, dto);
			ParkingDto parking = parkingPlaceCache.get(dto.getParkingPlace());
			CompanyDto company = companyCache.get(dto.getCompany());

			dto.setCppAddress(parking.getAddress());
			dto.setCompany(company.getCompanyName());
			return dto;
		}).forEachOrdered((dto) -> {
			rtn.add(dto);
		});
		return rtn;
	}

	@PostConstruct
	public void initialize() {
		mapper = new ModelMapper();
	}

	@Override
	public List<ParkingContractDto> findAll(ParkingContractCriteria ticketCriteria) {
		List<ParkingContractEntity> ticketEntities = null;
		if (ticketCriteria.getFromDate() != null && ticketCriteria.getToDate() != null)
			ticketEntities = parkingContractRepository.findAll(ticketCriteria.getCppCode(),
					ticketCriteria.getFromDate().getTime(), ticketCriteria.getToDate().getTime());

		List<ParkingContractDto> ticketDtos = this.map(ticketEntities);
		return ticketDtos;
	}

	@Override
	public List<ParkingContractOutOfDateDto> findParkingContractOutOfDate(ParkingContractCriteria ticketCriteria,
			Pageable pageable) {
		List<ParkingContractOutOfDateEntity> ticketEntities = null;
		ticketEntities = parkingContractOutOfDateRepository.findParkingContractOutOfDate(ticketCriteria.getCppCode(),
				pageable);
		List<ParkingContractOutOfDateDto> ticketDtos = this.map2(ticketEntities);
		return ticketDtos;
	}

	public List<ParkingContractOutOfDateDto> map2(List<ParkingContractOutOfDateEntity> source) {

		ArrayList<ParkingContractOutOfDateDto> rtn = new ArrayList<>();
		source.stream().map((entity) -> {
			ParkingContractOutOfDateDto dto = new ParkingContractOutOfDateDto();
			mapper.map(entity, dto);
			return dto;
		}).forEachOrdered((dto) -> {
			rtn.add(dto);
		});
		return rtn;
	}

	@Override
	public List<ParkingContractInfoDto> findParkingContractInfo(ParkingContractCriteria ticketCriteria) {
		List<ParkingContractInfoEntity> ticketEntities = null;
		ticketEntities = parkingContractInfoRepository.findParkingContractInfo(ticketCriteria.getCppCode());
		List<ParkingContractInfoDto> ticketDtos = this.map3(ticketEntities);
		return ticketDtos;
	}

	public List<ParkingContractInfoDto> map3(List<ParkingContractInfoEntity> source) {

		ArrayList<ParkingContractInfoDto> rtn = new ArrayList<>();
		source.stream().map((entity) -> {
			ParkingContractInfoDto dto = new ParkingContractInfoDto();
			mapper.map(entity, dto);
			return dto;
		}).forEachOrdered((dto) -> {
			rtn.add(dto);
		});
		return rtn;
	}

	@Override
	public List<ParkingContractEntity> findByParkingPlace(String code) {
		return parkingContractRepository.findParkingContractForInvoice(code);
	}

	@Override
	public List<ParkingContractEntity> findByCompany(String company) {
		return parkingContractRepository.findParkingContractByCompany(company);
	}

	@Override
	public ParkingContractOutOfDateEndPointDto findParkingContractOutOfDateFooter(
			ParkingContractCriteria ticketCriteria) {
		// TODO Auto-generated method stub
		ParkingContractOutOfDateEndPointDto parkingContractOutOfDateEndPointDto = new ParkingContractOutOfDateEndPointDto();
		ParkingContractOutOfDateFooterEntity footerEntity = null;
		footerEntity = parkingContractOutOfDateFooterRepository.findParkingContractOutOfDate(ticketCriteria.getCppCode());
		if (footerEntity == null) {
			footerEntity = new ParkingContractOutOfDateFooterEntity();
		}
		mapper.map(footerEntity, parkingContractOutOfDateEndPointDto);
		return parkingContractOutOfDateEndPointDto;
	}

}

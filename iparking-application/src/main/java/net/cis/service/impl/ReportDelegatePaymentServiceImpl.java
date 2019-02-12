package net.cis.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cis.dto.ParkingDto;
import net.cis.dto.ReportDelegatePaymentDto;
import net.cis.jpa.entity.ReportDelegatePaymentEntity;
import net.cis.repository.ReportDelegatePaymentRepository;
import net.cis.service.ParkingService;
import net.cis.service.ReportDelegatePaymentService;

/**
 * Created by Vincent on 02/10/2018
 */
@Service
public class ReportDelegatePaymentServiceImpl implements ReportDelegatePaymentService {

	@Autowired
	private ReportDelegatePaymentRepository reportDelegatePaymentRepository;

	@Autowired
	ParkingService parkingService;

	ModelMapper mapper;

	@PostConstruct
	public void initialize() {
		mapper = new ModelMapper();

	}

	@Override
	public List<ReportDelegatePaymentDto> findByCarppIdsAndDate(List<Long> carppIds, String date) {
		// danh sach diem do
		List<ParkingDto> lstParkingDto = parkingService.findByCarppIds(carppIds);
		// danh sach thanh toan ho
		List<ReportDelegatePaymentEntity> entities = reportDelegatePaymentRepository
				.findByCarppIdInAndCreatedDate(carppIds, date);
		return this.map(lstParkingDto, entities);
	}

	private List<ReportDelegatePaymentDto> map(List<ParkingDto> lstParkingDto,
			List<ReportDelegatePaymentEntity> source) {
		ArrayList<ReportDelegatePaymentDto> rtn = new ArrayList<>();
		for (ParkingDto objParkingDto : lstParkingDto) {
			ReportDelegatePaymentDto dto = new ReportDelegatePaymentDto();
			dto.setCarppId(objParkingDto.getOldId());
			dto.setAddress(objParkingDto.getAddress());
			dto.setCode(objParkingDto.getParkingCode());
			dto.setAmount(0L);
			for (ReportDelegatePaymentEntity entity : source) {
				if (dto.getCarppId() == entity.getCarppId()) {
					dto.setAmount(entity.getAmount());
					break;
				}
			}
			rtn.add(dto);
		}
		return rtn;
	}

}

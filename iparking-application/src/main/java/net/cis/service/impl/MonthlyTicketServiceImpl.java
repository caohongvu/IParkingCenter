package net.cis.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.cis.dto.MonthlyTicketDto;
import net.cis.jpa.criteria.MonthlyTicketCriteria;
import net.cis.jpa.entity.MonthlyTicketEntity;
import net.cis.repository.MonthlyTicketRepository;
import net.cis.repository.TicketRepository;
import net.cis.service.MonthlyTicketService;
import net.cis.service.cache.MonthlyTicketCache;

/**
 * Created by Vincent on 02/10/2018
 */
@Service
public class MonthlyTicketServiceImpl implements MonthlyTicketService {

	@Autowired
	private MonthlyTicketRepository monthlyTicketRepository;
	
	@Autowired 
	private TicketRepository ticketRepository;
	

	@Autowired
	private MonthlyTicketCache monthlyTicketCache;

	ModelMapper mapper;
	
	
	@Override
	public MonthlyTicketDto findOne(long id) {
		MonthlyTicketEntity entity = monthlyTicketRepository.findOne(id);
		MonthlyTicketDto dto = new MonthlyTicketDto();
		mapper.map(entity, dto);
		Long relatedTicket = monthlyTicketCache.get(entity.getId());
		if(relatedTicket != null) {
			dto.setInSession(true);
			dto.setInSessionTicketId(relatedTicket);
		}
		
		return dto;
	}
	

	@Override
	public List<MonthlyTicketDto> findAll(MonthlyTicketCriteria ticketCriteria, Pageable pageable) {
		
		List<MonthlyTicketEntity> ticketEntities = null;
		
		ticketEntities = monthlyTicketRepository.findAll(ticketCriteria.getCppCode(), pageable);
		
		List<MonthlyTicketDto> ticketDtos = this.map(ticketEntities);
		return ticketDtos;
	}

	
	private List<MonthlyTicketDto> map(List<MonthlyTicketEntity> source) {
		
		ArrayList<MonthlyTicketDto> rtn = new ArrayList<>();
		source.stream().map((entity) -> {
			MonthlyTicketDto dto = new MonthlyTicketDto();
			mapper.map(entity, dto);
			Long relatedTicket = monthlyTicketCache.get(entity.getId());
			if(relatedTicket != null) {
				dto.setInSession(true);
				dto.setInSessionTicketId(relatedTicket);
			}
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


	



}

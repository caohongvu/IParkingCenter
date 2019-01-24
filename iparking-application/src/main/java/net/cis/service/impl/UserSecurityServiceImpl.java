package net.cis.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import net.cis.dto.ResponseApi;
import net.cis.dto.UserSecurityDto;
import net.cis.jpa.entity.UserSecurityEntity;
import net.cis.repository.UserSecurityRepository;
import net.cis.service.UserSecurityService;

public class UserSecurityServiceImpl implements UserSecurityService{
	
	@Autowired
	private UserSecurityRepository userSecurityRepository;
	
	
	ModelMapper mapper;


	@Override
	public ResponseApi save(UserSecurityDto userSecurityDto) {
		UserSecurityEntity userSecurity = new UserSecurityEntity();
		mapper.map(userSecurityDto, userSecurity);
		ResponseApi api = new ResponseApi();
		return api;
	}

}

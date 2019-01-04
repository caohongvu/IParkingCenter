package net.cis.service.cache;

import java.util.HashMap;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import net.cis.dto.CompanyDto;

@Service
@Scope("singleton")
public class CompanyCache extends HashMap<String, CompanyDto>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
}

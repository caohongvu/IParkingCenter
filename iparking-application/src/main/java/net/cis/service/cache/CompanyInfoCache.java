package net.cis.service.cache;

import java.util.HashMap;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import net.cis.jpa.entity.CompanyInforEntity;

@Service
@Scope("singleton")
public class CompanyInfoCache extends HashMap<Integer, CompanyInforEntity>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
}

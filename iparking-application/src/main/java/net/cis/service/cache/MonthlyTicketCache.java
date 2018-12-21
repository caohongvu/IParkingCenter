package net.cis.service.cache;

import java.util.HashMap;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class MonthlyTicketCache extends HashMap<Long, Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
}

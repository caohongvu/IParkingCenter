package net.cis.service.cache;

import java.util.HashMap;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import net.cis.dto.ParkingDto;

@Service
@Scope("singleton")
public class ParkingPlaceCache extends HashMap<String, ParkingDto>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
}

package net.cis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import net.cis.dto.ParkingDto;
import net.cis.service.ParkingService;

@RestController
@RequestMapping("/parking")
@Api(value="parking Endpoint", description="The URL to handle parking endpoint")
public class ParkingPlaceEndpoint {
	
	@Autowired
	ParkingService parkingService;
	
	@RequestMapping(value="/{id}/getByOldId", method= RequestMethod.GET)
	public @ResponseBody ParkingDto getByOldId(@PathVariable("id") String oldId) throws Exception{
		ParkingDto parkingdto = parkingService.findByOldId(oldId);
		return parkingdto;
	}
}

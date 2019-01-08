package net.cis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import net.cis.common.util.StatusUtil;
import net.cis.dto.ErrorDto;
import net.cis.dto.ParkingActorDto;
import net.cis.dto.ResponseApi;
import net.cis.service.ParkingActorService;

@RestController
@RequestMapping("/parking-actor")
@Api(value="parking Endpoint", description="The URL to handle parking endpoint")
public class ParkingActorEndpoint {
	
	@Autowired
	ParkingActorService parkingActorService;
	
	
	@RequestMapping(value="/{id}/find-by-actor/", method= RequestMethod.GET)
	public @ResponseBody ResponseApi getByOldId(@PathVariable("id") long actorId) throws Exception{
		List<ParkingActorDto> parkingActorDtos = parkingActorService.findByActors(actorId);
		ErrorDto error = new ErrorDto();
		error.setCode(StatusUtil.SUCCESS_STATUS);
		error.setMessage(StatusUtil.SUCCESS_MESSAGE);
		
		ResponseApi responseApi = new ResponseApi();
		responseApi.setData(parkingActorDtos);
		
		responseApi.setError(error);
		
		return responseApi;
	}
	
}

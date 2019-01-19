package net.cis.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

/**
 * 
 * @author liemnh
 *
 */
@RestController
@RequestMapping("/parking-config")
@Api(value = "Parking config Endpoint", description = "The URL to handle Parking config endpoint")
public class ParkingConfigEndPoint {

}

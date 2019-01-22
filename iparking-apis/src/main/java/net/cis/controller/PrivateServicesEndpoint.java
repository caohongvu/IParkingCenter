package net.cis.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/private-service")
@Api(value = "private-service Endpoint", description = "The URL to handle private-service endpoint")
public class PrivateServicesEndpoint {

}

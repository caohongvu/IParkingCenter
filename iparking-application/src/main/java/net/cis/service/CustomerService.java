package net.cis.service;

import java.util.Map;

public interface CustomerService {
	Map<String,Object> createCustomerInPoseidonDb(String phone) throws Exception;
}

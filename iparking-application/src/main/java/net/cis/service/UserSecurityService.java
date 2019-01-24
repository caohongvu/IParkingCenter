package net.cis.service;

import java.util.List;

import net.cis.dto.MenuDto;
import net.cis.dto.ResponseApi;
import net.cis.dto.UserSecurityDto;

public interface UserSecurityService {
	ResponseApi save(UserSecurityDto userSecurityDto);

	UserSecurityDto findByUsername(String username);

	List<MenuDto> getMenuByRole(Integer roleId);
}

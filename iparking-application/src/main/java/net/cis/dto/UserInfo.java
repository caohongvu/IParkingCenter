package net.cis.dto;

import java.util.List;

public class UserInfo {
	private String token;
	private List<MenuDto> menus;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<MenuDto> getMenus() {
		return menus;
	}

	public void setMenus(List<MenuDto> menus) {
		this.menus = menus;
	}

}

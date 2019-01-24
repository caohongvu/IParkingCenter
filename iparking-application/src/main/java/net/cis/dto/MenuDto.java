package net.cis.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class MenuDto {
	private long id;
	private String name;
	private String label;
	private String description;
	private Integer level;
	private long parent_id;
	@JsonInclude(Include.NON_NULL)
	private Integer sort;
	@JsonInclude(Include.NON_NULL)
	private List<MenuDto> menuChilds;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public long getParent_id() {
		return parent_id;
	}

	public void setParent_id(long parent_id) {
		this.parent_id = parent_id;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public List<MenuDto> getMenuChilds() {
		return menuChilds;
	}

	public void setMenuChilds(List<MenuDto> menuChilds) {
		this.menuChilds = menuChilds;
	}

}

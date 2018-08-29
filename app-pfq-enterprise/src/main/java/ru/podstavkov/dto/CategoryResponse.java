package ru.podstavkov.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CategoryResponse extends AbstractRequest{
	
	@Size(max = 1024, min = 3, message = "{error.size}")
	@NotBlank(message = "{error.empty}")
	private String description;

	@NotBlank(message = "{error.empty}")
	private String menuname;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}
	
	

}

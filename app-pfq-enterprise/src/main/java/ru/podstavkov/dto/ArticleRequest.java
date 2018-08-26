package ru.podstavkov.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ArticleRequest extends AbstractRequest {
	
	@Size(max = 256, min = 3, message = "{error.size}")
	@NotBlank(message = "{error.empty}")
	private String content;
	
	private boolean active;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	private Date endDate;
	
    private  Map<String,String> category;
    
    private  Map<String,String> tegs;

	public ArticleRequest() {
		super();
		this.active = true;
		this.category = new LinkedHashMap<>();
		this.tegs = new LinkedHashMap<>();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Map<String, String> getCategory() {
		return category;
	}

	public void setCategory(Map<String, String> category) {
		this.category = category;
	}

	public Map<String, String> getTegs() {
		return tegs;
	}

	public void setTegs(Map<String, String> tegs) {
		this.tegs = tegs;
	}

	
	
	
    
    
}

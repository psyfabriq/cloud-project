package ru.podstavkov.dto;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ArticleRequest extends AbstractRequest {
	
	@Size(max = 256, min = 3, message = "{error.size}")
	@NotBlank(message = "{error.empty}")
	private String content;
	
	private Boolean active;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	@DateTimeFormat(pattern="dd/MM/YYYY HH:mm:ss")
	private Date endDate;
	
    private  Map<String,Item> categories;
    
    private  Map<String,Item> tegs;

	public ArticleRequest() {
		super();
		this.active = true;
		this.categories = new LinkedHashMap<>();
		this.tegs = new LinkedHashMap<>();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public Map<String, Item> getTegs() {
		return tegs;
	}

	public void setTegs(Map<String, Item> tegs) {
		this.tegs = tegs;
	}



	public Map<String, Item> getCategories() {
		return categories;
	}

	public void setCategories(Map<String, Item> categories) {
		this.categories = categories;
	}

	
	
	
    
    
}

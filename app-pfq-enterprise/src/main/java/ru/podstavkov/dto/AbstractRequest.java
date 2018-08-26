package ru.podstavkov.dto;

import java.time.Instant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AbstractRequest {
	
	protected String id="";

	@Size(max = 128, min = 3, message = "{error.size}")
	@NotBlank(message = "{error.empty}")
	protected String name;
	
	protected Instant createdAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}
	
	
	
	
}

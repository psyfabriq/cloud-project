package ru.podstavkov.dto;

public class Item {
	String id;
	String name;
	Boolean chose;
	
	
	
	public Item() {
		super();
	}

	public Item(String id, String name, Boolean chose) {
		super();
		this.id = id;
		this.name = name;
		this.chose = chose;
	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Boolean getChose() {
		return chose;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setChose(Boolean chose) {
		this.chose = chose;
	}

	

	
	
}

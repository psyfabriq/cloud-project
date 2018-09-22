package ru.podstavkov.model;

import java.util.ArrayList;
import java.util.List;

public class MenuItem implements Cloneable {
	private String text;
	private String uri;
	private boolean active;
	private String css;
	private List<MenuItem> children;

	public MenuItem() {
		this.children = new ArrayList<>();
	}

	public MenuItem(String text, String uri, boolean active, String css) {
		super();
		this.text = text;
		this.uri = uri;
		this.active = active;
		this.css = css;
		this.children = new ArrayList<>();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public List<MenuItem> getChildren() {
		return children;
	}

	public void setChildren(List<MenuItem> children) {
		this.children = children;
	}

	public MenuItem clone() throws CloneNotSupportedException {
		return (MenuItem) super.clone();
	}

}

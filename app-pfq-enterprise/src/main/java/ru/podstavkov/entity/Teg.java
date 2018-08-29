package ru.podstavkov.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name = "teg")
public class Teg  extends AbstractEntity {

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	@JoinTable(name = "article_tegs", joinColumns = { @JoinColumn(name = "teg_id") }, inverseJoinColumns = {
			@JoinColumn(name = "article_id") })
	private List<Article> article;

	@Column(name = "description",columnDefinition = "TEXT")
	private String description;

	@Column(name = "menuname",columnDefinition = "VARCHAR(40)")
	private String menuname;
	
	@Column(name = "teg",columnDefinition = "VARCHAR(40)",unique=true,nullable=false)
	private String  teg;
	
	public Teg() {
		super();
		this.article = new ArrayList<>();
	}

	public List<Article> getArticle() {
		return article;
	}

	public void setArticle(List<Article> article) {
		this.article = article;
	}

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

	public String getTeg() {
		return teg;
	}

	public void setTeg(String teg) {
		this.teg = teg;
	}

	
}

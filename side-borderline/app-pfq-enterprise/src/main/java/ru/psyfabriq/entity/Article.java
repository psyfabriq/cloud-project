package ru.psyfabriq.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name = "article")
public class Article extends AbstractEntity {


    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;

    @Column(name = "active", columnDefinition = "INT(1)")
    private boolean active;

    @Column(name = "end_date", columnDefinition = "DATETIME")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endDate;


    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "article_categories",
            joinColumns = {@JoinColumn(name = "article_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")}
    )
    @Fetch(FetchMode.SELECT)
    private List<Category> category = new ArrayList();

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "article_tegs",
            joinColumns = {@JoinColumn(name = "article_id")},
            inverseJoinColumns = {@JoinColumn(name = "teg_id")}
    )
    @Fetch(FetchMode.SELECT)
    private List<Teg> tegs = new ArrayList();


    public Article() {
        super();
        this.category = new ArrayList<>();
        this.tegs = new ArrayList<>();
        this.endDate = new Date();
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

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public List<Teg> getTegs() {
        return tegs;
    }

    public void setTegs(List<Teg> tegs) {
        this.tegs = tegs;
    }


}

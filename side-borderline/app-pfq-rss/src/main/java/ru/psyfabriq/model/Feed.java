package ru.psyfabriq.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "feeds")
public class Feed {

    //@Indexed(unique = true)
    @Column(name = "feedUrl")
    private String feedUrl;

    @Column(name = "title")
    private String title;

    @Column(name = "lastBuild")
    //@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date lastBuildDate;


    @OneToOne(mappedBy = "feed")
    private Subscription subscription;

    public Feed() {
    }

    public Feed(String feedUrl, String title, Date lastBuildDate) {
        String id = UUID.randomUUID().toString();
        this.feedUrl = feedUrl;
        this.title = title;
        this.lastBuildDate = lastBuildDate;
    }

    public String getFeedUrl() {
        return feedUrl;
    }

    public void setFeedUrl(String feedUrl) {
        this.feedUrl = feedUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getLastBuildDate() {
        return lastBuildDate;
    }

    public void setLastBuildDate(Date lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }

}

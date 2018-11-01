package ru.psyfabriq.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "feed_items")
public class FeedItem {

    @Id
    private String id;

    @Column(name = "subscriptionId")
    private String subscriptionId;

    @Column(name = "guid")
    private String guid;

    @Column(name = "title")
    private String title;

    @Column(name = "descr")
    private String descr;

    @Column(name = "author")
    private String author;

    @Column(name = "pubData")
    private Date pubDate;

    public FeedItem() {
    }

    public FeedItem(String subscriptionId, String guid, String title, String descr, String author,
                    Date pubDate) {
        this.id = UUID.randomUUID().toString();
        this.subscriptionId = subscriptionId;
        this.guid = guid;
        this.title = title;
        this.descr = descr;
        this.author = author;
        this.pubDate = pubDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

}

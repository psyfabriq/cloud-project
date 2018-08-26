package ru.podstavkov.model;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "subscriptions")
public class Subscription {

	@Id
	private String id;

	@Column(name = "rssUrl")
	private String rssUrl;

	@Column(name = "subscrCount")
	private int subscrCount;

    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="feed_id")
	private Feed feed;

	public Subscription() {
	}

	public Subscription(String rssUrl, int subscrCount) {
		this.id		 = UUID.randomUUID().toString();
		this.rssUrl = rssUrl;
		this.subscrCount = subscrCount;
	}

	public Subscription( String rssUrl, int subscrCount, Feed feed) {
		this(rssUrl, subscrCount);
		this.feed = feed;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRssUrl() {
		return rssUrl;
	}

	public void setRssUrl(String rssUrl) {
		this.rssUrl = rssUrl;
	}

	public int getSubscrCount() {
		return subscrCount;
	}

	public void setSubscrCount(int subscrCount) {
		this.subscrCount = subscrCount;
	}

	public Feed getFeed() {
		return feed;
	}

	public void setFeed(Feed feed) {
		this.feed = feed;
	}

}

package ru.podstavkov.service;

import java.util.List;
import java.util.Set;

import ru.podstavkov.model.Subscription;

public interface SubscriptionService {

	List<Subscription> findActives();
	
	List<Subscription> findByrssUrls(Set<String> rssUrls);
	
	void incrementSubscriptionCounter(String rssUrl);
	
	void decrementSubscriptionCounter(String rssUrl);

}

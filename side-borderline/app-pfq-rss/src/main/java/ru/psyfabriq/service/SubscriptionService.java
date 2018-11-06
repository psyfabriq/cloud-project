package ru.psyfabriq.service;

import ru.psyfabriq.model.Subscription;

import java.util.List;
import java.util.Set;

public interface SubscriptionService {

    List<Subscription> findActives();

    List<Subscription> findByrssUrls(Set<String> rssUrls);

    void incrementSubscriptionCounter(String rssUrl);

    void decrementSubscriptionCounter(String rssUrl);

}

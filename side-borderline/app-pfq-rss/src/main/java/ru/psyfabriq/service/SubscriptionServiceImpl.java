package ru.psyfabriq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.psyfabriq.model.Subscription;
import ru.psyfabriq.repository.FeedItemRepository;
import ru.psyfabriq.repository.SubscriptionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    FeedItemRepository feedItemRepository;

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Override
    public void incrementSubscriptionCounter(String rssUrl) {
        if (rssUrl == null || rssUrl.isEmpty()) return;
        Subscription subscription = subscriptionRepository.findByRssUrl(rssUrl);
        int count = subscription.getSubscrCount();
        subscription.setSubscrCount(count++);
        subscriptionRepository.saveAndFlush(subscription);
    }

    @Override
    public void decrementSubscriptionCounter(String rssUrl) {
        if (rssUrl == null || rssUrl.isEmpty()) return;
        Subscription subscription = subscriptionRepository.findByRssUrl(rssUrl);
        int count = subscription.getSubscrCount();
        subscription.setSubscrCount(count++);
        subscriptionRepository.saveAndFlush(subscription);
    }

    @Override
    public List<Subscription> findActives() {
        Optional<List<Subscription>> feeds = subscriptionRepository.findBySubscrCountGreaterThan(0);
        return feeds.orElseGet(ArrayList::new);
    }

    @Override
    public List<Subscription> findByrssUrls(Set<String> rssUrls) {
        Optional<List<Subscription>> feeds = subscriptionRepository.findAllByRssUrl(rssUrls);
        return feeds.isPresent() ? feeds.get() : new ArrayList<>();
    }

}

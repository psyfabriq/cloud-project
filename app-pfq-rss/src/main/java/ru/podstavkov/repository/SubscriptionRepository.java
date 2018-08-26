package ru.podstavkov.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.podstavkov.model.Subscription;

@Repository
public interface SubscriptionRepository  extends JpaRepository<Subscription, String> {

	Subscription findByRssUrl(String rssUrl);
	
	Optional<List<Subscription>> findAllByRssUrl(Iterable<String> rssUrls);
	
	Optional<List<Subscription>> findBySubscrCountGreaterThan(int subscrCount);

	
}
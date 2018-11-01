package ru.psyfabriq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.psyfabriq.model.Subscription;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, String> {

    Subscription findByRssUrl(String rssUrl);

    Optional<List<Subscription>> findAllByRssUrl(Iterable<String> rssUrls);

    Optional<List<Subscription>> findBySubscrCountGreaterThan(int subscrCount);


}
package ru.psyfabriq.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.psyfabriq.model.FeedItem;

import java.util.List;


@Repository
public interface FeedItemRepository extends CrudRepository<FeedItem, String> {

    List<FeedItem> findBySubscriptionId(String subscriptionId);


}

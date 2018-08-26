package ru.podstavkov.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ru.podstavkov.model.FeedItem;


@Repository
public interface FeedItemRepository extends CrudRepository<FeedItem, String>{

	List<FeedItem> findBySubscriptionId(String subscriptionId);
	
	
}

package ru.podstavkov.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ru.podstavkov.model.Feed;


@Repository
public interface FeedRepository extends CrudRepository<Feed, String>{
	
}

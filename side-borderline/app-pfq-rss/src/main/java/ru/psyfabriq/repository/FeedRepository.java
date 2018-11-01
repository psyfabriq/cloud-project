package ru.psyfabriq.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.psyfabriq.model.Feed;


@Repository
public interface FeedRepository extends CrudRepository<Feed, String> {

}

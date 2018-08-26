package ru.podstavkov.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.podstavkov.entity.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, String> {

    List<Article> findByIdIn(List<String> articleIds);

    Optional<Article> findByName(String name);

    Boolean existsByName(String name);

}

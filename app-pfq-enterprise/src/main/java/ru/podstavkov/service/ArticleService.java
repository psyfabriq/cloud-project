package ru.podstavkov.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ru.podstavkov.dto.ArticleRequest;
import ru.podstavkov.dto.ArticleResponse;
import ru.podstavkov.dto.TegRequest;
import ru.podstavkov.dto.TegResponse;
import ru.podstavkov.entity.Article;
import ru.podstavkov.entity.Teg;

public interface ArticleService extends ExecutestService<Article,ArticleRequest, ArticleResponse> {
    Article add(Article article);
    void delete(String id);
    Article getByName(String name);
    Article getByID(String id);
    Article edit(Article article);
    List<Article > getAll();
    Boolean existsByName(String name);
    Boolean existsByID(String id);

}

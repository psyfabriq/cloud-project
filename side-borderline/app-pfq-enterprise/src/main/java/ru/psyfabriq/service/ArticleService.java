package ru.psyfabriq.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ru.psyfabriq.dto.ArticleRequest;
import ru.psyfabriq.dto.ArticleResponse;
import ru.psyfabriq.dto.TegRequest;
import ru.psyfabriq.dto.TegResponse;
import ru.psyfabriq.entity.Article;
import ru.psyfabriq.entity.Teg;

public interface ArticleService extends ExecutestService<Article, ArticleRequest, ArticleResponse> {
    Article add(Article article);

    void delete(String id);

    Article getByName(String name);

    Article getByID(String id);

    Article edit(Article article);

    List<Article> getAll();

    Boolean existsByName(String name);

    Boolean existsByID(String id);

}

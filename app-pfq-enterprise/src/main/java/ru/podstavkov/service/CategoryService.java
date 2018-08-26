package ru.podstavkov.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ru.podstavkov.dto.ArticleRequest;
import ru.podstavkov.dto.ArticleResponse;
import ru.podstavkov.dto.CategoryRequest;
import ru.podstavkov.dto.CategoryResponse;
import ru.podstavkov.entity.Article;
import ru.podstavkov.entity.Category;

public interface CategoryService extends ExecutestService<Category,CategoryRequest,CategoryResponse>{
    Category add(Category category);
    void delete(String id);
    Category getByName(String name);
    Category getByID(String id);
    Category edit(Category category);
    List<Category > getAll();
    Boolean existsByName(String name);
    Boolean existsByID(String id);

}

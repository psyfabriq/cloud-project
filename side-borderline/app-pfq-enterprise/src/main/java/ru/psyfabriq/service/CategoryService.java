package ru.psyfabriq.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ru.psyfabriq.dto.ArticleRequest;
import ru.psyfabriq.dto.ArticleResponse;
import ru.psyfabriq.dto.CategoryRequest;
import ru.psyfabriq.dto.CategoryResponse;
import ru.psyfabriq.entity.Article;
import ru.psyfabriq.entity.Category;

public interface CategoryService extends ExecutestService<Category, CategoryRequest, CategoryResponse> {
    Category add(Category category);

    void delete(String id);

    Category getByName(String name);

    Category getByID(String id);

    Category edit(Category category);

    List<Category> getAll();

    Boolean existsByName(String name);

    Boolean existsByID(String id);

}

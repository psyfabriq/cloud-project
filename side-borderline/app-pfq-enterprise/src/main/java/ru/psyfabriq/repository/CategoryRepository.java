package ru.psyfabriq.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.psyfabriq.entity.Article;
import ru.psyfabriq.entity.Category;
import ru.psyfabriq.entity.User;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    List<Category> findByIdIn(List<String> categoryIds);

    Optional<Category> findByName(String name);

    Boolean existsByName(String name);

}

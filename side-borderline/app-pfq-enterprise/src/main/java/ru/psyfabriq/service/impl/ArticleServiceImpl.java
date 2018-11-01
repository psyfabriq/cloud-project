package ru.psyfabriq.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ru.psyfabriq.dto.ArticleRequest;
import ru.psyfabriq.dto.ArticleResponse;
import ru.psyfabriq.dto.Item;
import ru.psyfabriq.entity.Article;
import ru.psyfabriq.entity.Category;
import ru.psyfabriq.entity.Teg;
import ru.psyfabriq.model.UserPrincipal;
import ru.psyfabriq.repository.ArticleRepository;
import ru.psyfabriq.repository.CategoryRepository;
import ru.psyfabriq.repository.TegRepository;
import ru.psyfabriq.service.ArticleService;
import ru.psyfabriq.utils.annotation.CurrentUser;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TegRepository tegRepository;


    @Override
    public Article add(Article article) {
        return articleRepository.saveAndFlush(article);
    }

    @Override
    public void delete(String id) {
        articleRepository.deleteById(id);
    }

    @Override
    public Article getByName(String name) {
        return articleRepository.findByName(name).get();
    }

    @Override
    public Article edit(Article user) {
        return articleRepository.saveAndFlush(user);
    }

    @Override
    public List<Article> getAll() {
        return articleRepository.findAll();
    }

    @Override
    public Boolean existsByName(String name) {
        return articleRepository.existsByName(name);
    }

    @Override
    public Page<ArticleResponse> findPaginated(Pageable pageable) {

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<ArticleResponse> list;
        List<Article> articles = getAll();

        if (articles.size() < startItem) {
            list = Collections.emptyList();
        } else {
            list = new ArrayList<>();
            int toIndex = Math.min(startItem + pageSize, articles.size());
            articles.subList(startItem, toIndex).forEach((v) -> {
                list.add(convertEntityToDTOResponse(v).get());
            });
        }

        Page<ArticleResponse> articlePage = new PageImpl<ArticleResponse>(list, PageRequest.of(currentPage, pageSize),
                articles.size());

        return articlePage;
    }

    @Override
    public Boolean existsByID(String id) {
        return articleRepository.existsById(id);
    }

    @Override
    public Article getByID(String id) {
        return articleRepository.getOne(id);
    }

    @Override
    public Optional<Article> convertDtoToEntityRequest(ArticleRequest dto) {
        Optional<Article> tmp = articleRepository.findById(dto.getId());
        Article article = tmp.isPresent() ? tmp.get() : new Article();
        article.setActive(dto.getActive());
        article.setContent(dto.getContent());
        article.setEndDate(dto.getEndDate());
        article.setName(dto.getName());

        //	List<String> categoriesIDs = new ArrayList<>(dto.getCategory().keySet());

        List<String> categoriesIDs = new ArrayList<>(dto.getCategories().entrySet().stream()
                .filter(map -> map.getValue().getChose() == true)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).keySet());

        List<Category> categories = categoryRepository.findByIdIn(categoriesIDs);
        article.setCategory(categories);

        //List<String> tegsIDs = new ArrayList<>(dto.getTegs().keySet());
        List<String> tegsIDs = new ArrayList<>(dto.getTegs().entrySet().stream()
                .filter(map -> map.getValue().getChose() == true)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).keySet());

        List<Teg> tegs = tegRepository.findByIdIn(tegsIDs);
        article.setTegs(tegs);

        return Optional.ofNullable(article);
    }

    @Override
    public Optional<ArticleRequest> convertEntityToDTORequest(Article entity) {
        ArticleRequest articleRequest = new ArticleRequest();
        articleRequest.setName(entity.getName());
        articleRequest.setActive(entity.isActive());
        articleRequest.setContent(entity.getContent());
        articleRequest.setId(entity.getId());
        articleRequest.setEndDate(entity.getEndDate());

        //  Map<String,Item> category  = new LinkedHashMap<>();
        //   Map<String,Item> tegs      = new LinkedHashMap<>();

        Map<String, Item> category = categoryRepository.findAll().stream().collect(Collectors.toMap(Category::getId, Category -> new Item(Category.getId(), Category.getName(), false)));
        Map<String, Item> tegs = tegRepository.findAll().stream().collect(Collectors.toMap(Teg::getId, Teg -> new Item(Teg.getId(), Teg.getName(), false)));


        entity.getCategory().forEach((v) -> {
            if (category.containsKey(v.getId())) {
                Item item = category.get(v.getId());
                item.setChose(true);
                category.put(v.getId(), item);

            } else {
                category.put(v.getId(), new Item(v.getId(), v.getName(), true));
            }
        });

        entity.getTegs().forEach((v) -> {
            if (tegs.containsKey(v.getId())) {
                Item item = tegs.get(v.getId());
                item.setChose(true);
                tegs.put(v.getId(), item);
            } else {
                tegs.put(v.getId(), new Item(v.getId(), v.getName(), true));
            }
        });

        articleRequest.setCategories(category);
        articleRequest.setTegs(tegs);

        return Optional.ofNullable(articleRequest);
    }

    @Override
    public Optional<Article> convertDtoToEntityResponse(ArticleResponse dto) {
        Article article = articleRepository.findById(dto.getId()).get();
        article.setActive(dto.isActive());
        article.setContent(dto.getContent());
        article.setEndDate(dto.getEndDate());
        article.setName(dto.getName());

        List<String> categoriesIDs = new ArrayList<>(dto.getCategory().keySet());

        List<Category> categories = categoryRepository.findByIdIn(categoriesIDs);
        article.setCategory(categories);

        List<String> tegsIDs = new ArrayList<>(dto.getTegs().keySet());
        List<Teg> tegs = tegRepository.findByIdIn(tegsIDs);
        article.setTegs(tegs);

        return Optional.ofNullable(article);
    }

    @Override
    public Optional<ArticleResponse> convertEntityToDTOResponse(Article entity) {
        ArticleResponse articleResponse = new ArticleResponse();
        articleResponse.setName(entity.getName());
        articleResponse.setActive(entity.isActive());
        articleResponse.setContent(entity.getContent());
        articleResponse.setId(entity.getId());
        articleResponse.setEndDate(entity.getEndDate());

        Map<String, String> category = new LinkedHashMap<>();
        Map<String, String> tegs = new LinkedHashMap<>();

        entity.getCategory().forEach((v) -> {
            category.put(v.getId(), v.getName());
        });

        entity.getTegs().forEach((v) -> {
            tegs.put(v.getId(), v.getName());
        });


        articleResponse.setCategory(category);
        articleResponse.setTegs(tegs);
        return Optional.ofNullable(articleResponse);
    }

}

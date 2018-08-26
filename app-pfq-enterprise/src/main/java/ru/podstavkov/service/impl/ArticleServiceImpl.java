package ru.podstavkov.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ru.podstavkov.dto.ArticleRequest;
import ru.podstavkov.dto.ArticleResponse;
import ru.podstavkov.entity.Article;
import ru.podstavkov.entity.Category;
import ru.podstavkov.entity.Teg;
import ru.podstavkov.model.UserPrincipal;
import ru.podstavkov.repository.ArticleRepository;
import ru.podstavkov.repository.CategoryRepository;
import ru.podstavkov.repository.TegRepository;
import ru.podstavkov.service.ArticleService;
import ru.podstavkov.utils.annotation.CurrentUser;

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
	public Optional<ArticleRequest> convertEntityToDTORequest(Article entity) {
		ArticleRequest articleRequest = new ArticleRequest();
		articleRequest.setName(entity.getName());
		articleRequest.setActive(entity.isActive());
		articleRequest.setContent(entity.getContent());
		articleRequest.setId(entity.getId());
		articleRequest.setEndDate(entity.getEndDate());
		
	    Map<String,String> category  = new LinkedHashMap<>();
	    Map<String,String> tegs  = new LinkedHashMap<>();
		
		entity.getCategory().forEach((v)->{
			category.put(v.getId(), v.getName());
		});
		
		entity.getTegs().forEach((v)->{
			tegs.put(v.getId(), v.getName());
		});
		
		
		articleRequest.setCategory(category);
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
		
	    Map<String,String> category  = new LinkedHashMap<>();
	    Map<String,String> tegs  = new LinkedHashMap<>();
		
		entity.getCategory().forEach((v)->{
			category.put(v.getId(), v.getName());
		});
		
		entity.getTegs().forEach((v)->{
			tegs.put(v.getId(), v.getName());
		});
		
		
		articleResponse.setCategory(category);
		articleResponse.setTegs(tegs);
		return Optional.ofNullable(articleResponse);
	}

}

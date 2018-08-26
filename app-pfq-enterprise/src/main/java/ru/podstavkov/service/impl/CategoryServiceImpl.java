package ru.podstavkov.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ru.podstavkov.dto.CategoryRequest;
import ru.podstavkov.dto.CategoryResponse;
import ru.podstavkov.entity.Category;
import ru.podstavkov.repository.CategoryRepository;
import ru.podstavkov.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public Category add(Category category) {
		return categoryRepository.saveAndFlush(category);
	}

	@Override
	public void delete(String id) {
		categoryRepository.deleteById(id);
	}

	@Override
	public Category getByName(String name) {
		return categoryRepository.findByName(name).get();
	}

	@Override
	public Category edit(Category category) {
		return categoryRepository.saveAndFlush(category);
	}

	@Override
	public List<Category> getAll() {
		return categoryRepository.findAll();
	}

	@Override
	public Boolean existsByName(String name) {
		return categoryRepository.existsByName(name);
	}

	@Override
	public Page<CategoryResponse> findPaginated(Pageable pageable) {

		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<CategoryResponse> list;
		List<Category> categories = getAll();

		if (categories.size() < startItem) {
			list = Collections.emptyList();
		} else {
			list = new ArrayList<>();
			int toIndex = Math.min(startItem + pageSize, categories.size());
			categories.subList(startItem, toIndex).forEach((v) -> {
				list.add(convertEntityToDTOResponse(v).get());
			});
		}

		Page<CategoryResponse> categoryPage = new PageImpl<CategoryResponse>(list,
				PageRequest.of(currentPage, pageSize), categories.size());

		return categoryPage;
	}

	@Override
	public Boolean existsByID(String id) {
		return categoryRepository.existsById(id);
	}

	@Override
	public Category getByID(String id) {
		return categoryRepository.getOne(id);
	}

	@Override
	public Optional<Category> convertDtoToEntityRequest(CategoryRequest dto) {
		
		Category category;
		if(categoryRepository.existsById(dto.getId())) {
			category = categoryRepository.findById(dto.getId()).get();
		}else {
		    category = new Category();
		    category.setId(dto.getId());
		}
		
		category.setName(dto.getName());
		category.setMenuname(dto.getMenuname());
		category.setDescription(dto.getDescription());
		
		return Optional.ofNullable(category);
	}

	@Override
	public Optional<CategoryRequest> convertEntityToDTORequest(Category entity) {
		CategoryRequest categoryRequest = new CategoryRequest();
		categoryRequest.setCreatedAt(entity.getCreatedAt());
		categoryRequest.setDescription(entity.getDescription());
		categoryRequest.setId(entity.getId());
		categoryRequest.setMenuname(entity.getMenuname());
		categoryRequest.setName(entity.getName());
		return Optional.ofNullable(categoryRequest);
	}

	@Override
	public Optional<Category> convertDtoToEntityResponse(CategoryResponse dto) {
		Category category;
		if(categoryRepository.existsById(dto.getId())) {
			category = categoryRepository.findById(dto.getId()).get();
		}else {
		    category = new Category();
		    category.setId(dto.getId());
		}
		
		category.setName(dto.getName());
		category.setMenuname(dto.getMenuname());
		category.setDescription(dto.getDescription());
		
		return Optional.ofNullable(category);
	}

	@Override
	public Optional<CategoryResponse> convertEntityToDTOResponse(Category entity) {
		CategoryResponse categoryResponse = new CategoryResponse();
		categoryResponse.setCreatedAt(entity.getCreatedAt());
		categoryResponse.setDescription(entity.getDescription());
		categoryResponse.setId(entity.getId());
		categoryResponse.setMenuname(entity.getMenuname());
		categoryResponse.setName(entity.getName());
		return Optional.ofNullable(categoryResponse);
	}

}

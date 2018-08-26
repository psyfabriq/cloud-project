package ru.podstavkov.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import ru.podstavkov.dto.CategoryRequest;
import ru.podstavkov.dto.CategoryResponse;
import ru.podstavkov.entity.Category;
import ru.podstavkov.service.CategoryService;
import ru.podstavkov.utils.annotation.ItemMenu;
import ru.podstavkov.utils.annotation.PageTitle;

@Controller
public class CategoryController {
	
    private static int currentPage = 1;
    private static int pageSize = 5;
	
	@Autowired
	CategoryService categoryService;
	
    @GetMapping("/secure/categories")
	@ItemMenu(uri="/secure/categories",menuname="admsidebar") 
    @PageTitle(code="adm.link.s.category")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')") //SpEL Type
    public String admListCategories(Map<String, Object> model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
    	
        page.ifPresent(p -> currentPage = p);
        size.ifPresent(s -> pageSize = s);
        
        Page<CategoryResponse> categoryPage = categoryService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.put("listItems", categoryPage);
        
        int totalPages = categoryPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            model.put("pageNumbers", pageNumbers);
        }
        
        return "admin/list-categories";
    }
    
    @GetMapping("/secure/categories/add")
	@ItemMenu(uri="/secure/categories",menuname="admsidebar") 
    @PageTitle(code="adm.link.category")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')") //SpEL Type
    public String admAddCategory(Map<String, Object> model) {	
    	Category category = new Category();
    	model.put("model", categoryService.convertEntityToDTORequest(category));
    	return "admin/edit-category";
    }
    
    @GetMapping("/secure/category/{id}")
	@ItemMenu(uri="/secure/categories",menuname="admsidebar") 
    @PageTitle(code="adm.link.category")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')") //SpEL Type
    public String admEditCategory(Map<String, Object> model,@PathVariable(value = "id") String id) {
    	if(!categoryService.existsByID(id)) {return "redirect: secure/categories";}
    	Category category = categoryService.getByID(id); 	
    	model.put("model", categoryService.convertEntityToDTORequest(category));
    	return "admin/edit-category";
    }
}

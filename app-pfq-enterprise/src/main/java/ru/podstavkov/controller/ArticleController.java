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

import ru.podstavkov.dto.ArticleResponse;
import ru.podstavkov.dto.UserProfile;
import ru.podstavkov.entity.Article;
import ru.podstavkov.entity.Teg;
import ru.podstavkov.service.ArticleService;
import ru.podstavkov.utils.annotation.ItemMenu;
import ru.podstavkov.utils.annotation.PageTitle;

@Controller
public class ArticleController {
	
    private static int currentPage = 1;
    private static int pageSize = 5;
	
	@Autowired
	ArticleService articleService;

    @GetMapping("/secure/articles")
	@ItemMenu(uri="/secure/articles",menuname="admsidebar") 
    @PageTitle(code="adm.link.s.article")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')") //SpEL Type
    public String admListArticles(Map<String, Object> model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
    	
        page.ifPresent(p -> currentPage = p);
        size.ifPresent(s -> pageSize = s); 
        Page<ArticleResponse> articlePage = articleService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
        model.put("listItems", articlePage);
        
        int totalPages = articlePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            model.put("pageNumbers", pageNumbers);
        }
        
        return "admin/list-articles";
    }  
    
    
	@GetMapping("/secure/articles/add")
	@ItemMenu(uri = "/secure/articles", menuname = "admsidebar")
	@PageTitle(code = "adm.link.article")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')") // SpEL Type
	public String admAddArticle(Map<String, Object> model) {
		Article article = new Article();
		model.put("model", articleService.convertEntityToDTORequest(article));
		return "admin/edit-article";
	}

	@GetMapping("/secure/articles/{id}")
	@ItemMenu(uri = "/secure/articles", menuname = "admsidebar")
	@PageTitle(code = "adm.link.s.article")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')") // SpEL Type
	public String admEditArticle(Map<String, Object> model, @PathVariable(value = "id") String id) {
		if (!articleService.existsByID(id)) {
			return "";
		}
		Article article = articleService.getByID(id);
		model.put("model", articleService.convertEntityToDTORequest(article));
		return "admin/edit-article";
	}
     
}

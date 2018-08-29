package ru.podstavkov.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.podstavkov.dto.CategoryRequest;
import ru.podstavkov.dto.TegRequest;
import ru.podstavkov.dto.TegResponse;
import ru.podstavkov.entity.Category;
import ru.podstavkov.entity.Teg;
import ru.podstavkov.service.TegService;
import ru.podstavkov.utils.annotation.ItemMenu;
import ru.podstavkov.utils.annotation.PageTitle;

@Controller
public class TegController {
	
    private static int currentPage = 1;
    private static int pageSize = 5;

	@Autowired
	TegService tegService;
	
    @GetMapping("/secure/tegs")
    @ItemMenu(uri="/secure/tegs",menuname="admsidebar") 
    @PageTitle(code="adm.link.s.teg")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')") //SpEL Type
    public String admListTegs(Map<String, Object> model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
    	
        page.ifPresent(p -> currentPage = p);
        size.ifPresent(s -> pageSize = s);
        Page<TegResponse> tegPage = tegService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
        model.put("listItems", tegPage);
        
        int totalPages = tegPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            model.put("pageNumbers", pageNumbers);
        }
        
        return "admin/list-tegs";
    }
    
    @GetMapping("/secure/tegs/add")
    @ItemMenu(uri="/secure/tegs",menuname="admsidebar") 
    @PageTitle(code="adm.link.teg")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')") //SpEL Type
    public String admAddTeg(Map<String, Object> model) {	
    	Teg teg = new Teg();
    	model.put("model", tegService.convertEntityToDTORequest(teg));
    	return "admin/edit-teg";
    }
    
    @GetMapping("/secure/teg/{id}")
    @ItemMenu(uri="/secure/tegs",menuname="admsidebar") 
    @PageTitle(code="adm.link.s.teg")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')") //SpEL Type
    public String admEditTeg(Map<String, Object> model,@PathVariable(value = "id") String id) {
    	if(!tegService.existsByID(id)) {return "";}
    	Teg teg = tegService.getByID(id); 	
    	model.put("model", tegService.convertEntityToDTORequest(teg));
    	return "admin/edit-teg";
    }
    
	@PostMapping("/secure/teg")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')") // SpEL Type
	public String admAction(HttpServletRequest request,@Valid  @ModelAttribute TegRequest obj) {
		Teg teg = tegService.convertDtoToEntityRequest(obj).get();
		tegService.edit(teg);
		return "redirect:/secure/tegs";
	}
}

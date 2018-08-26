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

import ru.podstavkov.entity.Role;
import ru.podstavkov.entity.User;
import ru.podstavkov.service.RoleService;
import ru.podstavkov.utils.annotation.ItemMenu;
import ru.podstavkov.utils.annotation.PageTitle;

@Controller
public class RoleController {
	
    private static int currentPage = 1;
    private static int pageSize = 5;
	
	@Autowired
	RoleService roleService;

    @GetMapping("/secure/roles")
    @ItemMenu(uri="/secure/roles",menuname="admsidebar") 
    @PageTitle(code="adm.link.s.role")
	@PreAuthorize("hasRole('ROLE_ADMIN')") //SpEL Type
    public String admListRoles(Map<String, Object> model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
    	
        page.ifPresent(p -> currentPage = p);
        size.ifPresent(s -> pageSize = s);
        
        Page<Role> rolePage = roleService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
        
        model.put("listItems", rolePage);
        
        int totalPages = rolePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            model.put("pageNumbers", pageNumbers);
        }
        

        return "admin/list-roles";
    }
    
	@GetMapping("/secure/roles/add")
	@ItemMenu(uri = "/secure/roles", menuname = "admsidebar")
	@PageTitle(code = "adm.link.role")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')") // SpEL Type
	public String admAddRole(Map<String, Object> model) {
		Role role = new Role();
		model.put("model", role);
		return "admin/edit-user";
	}

	@GetMapping("/secure/role/{id}")
	@ItemMenu(uri = "/secure/roles", menuname = "admsidebar")
	@PageTitle(code = "adm.link.s.role")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')") // SpEL Type
	public String admEditRole(Map<String, Object> model, @PathVariable(value = "id") String id) {
		if (!roleService.existsByName(id)) {
			return "";
		}
		Role role = roleService.getByName(id);
		model.put("model", role);
		return "admin/edit-user";
	}
}

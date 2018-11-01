package ru.psyfabriq.controller;

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

import ru.psyfabriq.dto.UserProfile;
import ru.psyfabriq.dto.UserRequest;
import ru.psyfabriq.entity.User;
import ru.psyfabriq.model.UserPrincipal;
import ru.psyfabriq.service.UserService;
import ru.psyfabriq.utils.annotation.CurrentUser;
import ru.psyfabriq.utils.annotation.ItemMenu;
import ru.psyfabriq.utils.annotation.PageTitle;

@Controller
public class UserController {

    private static int currentPage = 1;
    private static int pageSize = 5;

    @Autowired
    UserService userService;

    public UserController(UserService mocUserService) {
        userService = userService;
    }

    @GetMapping("/secure/users")
    @ItemMenu(uri = "/secure/users", menuname = "admsidebar")
    @PageTitle(code = "adm.link.s.user")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // SpEL Type
    public String admListUsers(Map<String, Object> model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {

        page.ifPresent(p -> currentPage = p);
        size.ifPresent(s -> pageSize = s);

        Page<UserProfile> userPage = userService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.put("listItems", userPage);

        int totalPages = userPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.put("pageNumbers", pageNumbers);
        }

        return "admin/list-users";
    }

    @GetMapping("/secure/profile")
    @ItemMenu(uri = "/secure/profile", menuname = "admtopbar")
    @PageTitle(code = "adm.link.profile")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')") // SpEL Type
    public String userProfile(Map<String, Object> model, @CurrentUser UserPrincipal currentUser) {
        model.put("profile", new UserRequest(userService.getByNameOrEmail(currentUser.getName(), currentUser.getEmail())));
        return "admin/profile";
    }

    @PostMapping("/secure/profile")
    //@ItemMenu(uri = "/secure/profile", menuname = "admtopbar")
    //@PageTitle(code = "adm.link.profile")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')") // SpEL Type
    public String userProfileSubmit(HttpServletRequest request, @Valid @ModelAttribute UserRequest obj) {
        userService.editUser(userService.convertDtoToEntityRequest(obj).get());
        //model.put("profile", userService.convertUserToDTO(userService.editUser(obj)).get());
        //	return "admin/profile";
        return "redirect:/secure/profile";
    }

    @PostMapping("/secure/user")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')") // SpEL Type
    public String admAction(HttpServletRequest request, @Valid @ModelAttribute UserRequest obj) {
        userService.editUser(userService.convertDtoToEntityRequest(obj).get());
        return "redirect:/secure/users";

    }

    @GetMapping("/secure/users/add")
    @ItemMenu(uri = "/secure/users", menuname = "admsidebar")
    @PageTitle(code = "adm.link.user")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')") // SpEL Type
    public String admAddUser(Map<String, Object> model) {
        User user = new User();
        model.put("model", userService.convertEntityToDTORequest(user));
        return "admin/edit-user";
    }

    @GetMapping("/secure/user/{code}")
    @ItemMenu(uri = "/secure/users", menuname = "admsidebar")
    @PageTitle(code = "adm.link.s.user")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')") // SpEL Type
    public String admEditUser(Map<String, Object> model, @PathVariable(value = "code") String code) {
        if (!userService.existsByCode(code)) {
            return "";
        }
        User user = userService.getByCode(code);
        model.put("model", userService.convertEntityToDTORequest(user));
        return "admin/edit-user";
    }

}

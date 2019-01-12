package ru.psyfabriq.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.psyfabriq.model.CustomPrincipal;

@RestController
public class ResourceController {

    @GetMapping("/admins")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String context() {
        CustomPrincipal principal = (CustomPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getUsername() + " " + principal.getEmail();
    }

    @GetMapping("/users")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public String secured(CustomPrincipal principal) {
        return principal.getUsername() + " " + principal.getEmail();
    }

    @GetMapping("/")
    public String general() {
        return "common api success";
    }

}
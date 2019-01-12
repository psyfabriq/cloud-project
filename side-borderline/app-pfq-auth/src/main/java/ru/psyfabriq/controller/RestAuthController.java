package ru.psyfabriq.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class RestAuthController {
    @RequestMapping("/user")
    Principal principal(Principal p) {
        return p;
    }
}


package ru.psyfabriq.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public abstract class AbstractController {

    @Value("${welcome.message:test}")
    protected String message = "Hello World";

}

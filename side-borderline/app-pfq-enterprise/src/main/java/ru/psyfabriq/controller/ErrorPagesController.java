package ru.psyfabriq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

import org.springframework.http.HttpStatus;


@Controller
public class ErrorPagesController {


    @RequestMapping("/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String unAuthorized(Map<String, Object> model) {
        return "error/401";
    }

    @RequestMapping("/404")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFound(Map<String, Object> model) {
        return "error/404";
    }

    @RequestMapping("/403")
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String forbidden(Map<String, Object> model) {
        return "error/403";
    }

    @RequestMapping("/500")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String internalServerError(Map<String, Object> model) {
        return "error/500";
    }

}

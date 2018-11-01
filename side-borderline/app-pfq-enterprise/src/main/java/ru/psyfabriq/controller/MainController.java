package ru.psyfabriq.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.psyfabriq.utils.annotation.LogBefore;

@Controller
public class MainController extends AbstractController {

    @RequestMapping("/")
    @LogBefore
    public String welcome(Map<String, Object> model) {
        model.put("message", message);
        return "site/welcome";
    }

    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }


    @GetMapping("/international")
    public String getInternationalPage(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }


}

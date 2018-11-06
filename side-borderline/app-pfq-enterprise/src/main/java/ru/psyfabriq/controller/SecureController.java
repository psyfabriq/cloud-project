package ru.psyfabriq.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ru.psyfabriq.utils.MenuAspect;
import ru.psyfabriq.utils.annotation.ItemMenu;
import ru.psyfabriq.utils.annotation.PageTitle;

@Controller
public class SecureController {

    private static final Logger log = LoggerFactory.getLogger(SecureController.class);


    @RequestMapping(value = "/secure", method = RequestMethod.GET)
    @ItemMenu(uri = "/secure", menuname = "admsidebar")
    @PageTitle(code = "adm.link.dashboard")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')") //SpEL Type
    public String welcome(Map<String, Object> model) {
        //	model.put("duri", duri);
        log.info(model.toString());
        return "admin/dashboard";
    }
} 

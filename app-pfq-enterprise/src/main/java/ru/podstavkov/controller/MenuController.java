package ru.podstavkov.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import ru.podstavkov.utils.annotation.ItemMenu;
import ru.podstavkov.utils.annotation.PageTitle;

@Controller
public class MenuController {

    @GetMapping("/secure/menus")
    @ItemMenu(uri="/secure/menus",menuname="admsidebar") 
    @PageTitle(code="adm.link.s.menu")
    public String admListMenus(Map<String, Object> model) {
        return "admin/list-menus";
    }
}

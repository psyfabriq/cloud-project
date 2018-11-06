package ru.psyfabriq.utils;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.psyfabriq.model.MenuItem;
import ru.psyfabriq.repository.impl.MenuRepository;
import ru.psyfabriq.utils.annotation.ItemMenu;

@Aspect
@Component
public class MenuAspect {

    private static final Logger log = LoggerFactory.getLogger(MenuAspect.class);

    @Autowired
    MenuRepository menuRepository;

    @Before(" @annotation(ru.psyfabriq.utils.annotation.ItemMenu)")
    public void logBefore(JoinPoint joinPoint) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ItemMenu itemMenu = method.getAnnotation(ItemMenu.class);

        Map<String, Collection<MenuItem>> allmenus = menuRepository.getAllMenus(itemMenu.menuname(), itemMenu.uri());

        Map<String, Object> mode = (Map<String, Object>) joinPoint.getArgs()[0];

        mode.putAll(allmenus);

    }

}

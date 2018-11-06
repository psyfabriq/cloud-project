package ru.psyfabriq.service.impl;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import ru.psyfabriq.entity.User;
import ru.psyfabriq.model.RoleName;
import ru.psyfabriq.service.RoleService;
import ru.psyfabriq.service.UserService;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup)
            return;

        roleService.createRoleIfNotFound(RoleName.ROLE_ADMIN.name());
        roleService.createRoleIfNotFound(RoleName.ROLE_USER.name());

        userService.getAll().forEach((v) -> {
            if (v.getCode() == null || v.getCode().isEmpty()) {
                v.setCode(UUID.randomUUID().toString());
                userService.editUser(v);
            }
        });

    }

}

package ru.psyfabriq.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.psyfabriq.entity.Account;
import ru.psyfabriq.entity.Client;
import ru.psyfabriq.entity.Permission;
import ru.psyfabriq.entity.Role;
import ru.psyfabriq.service.AccountService;
import ru.psyfabriq.service.ClientService;
import ru.psyfabriq.service.PermissionService;
import ru.psyfabriq.service.RoleService;
import ru.psyfabriq.utils.EncrytedPasswordUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final AccountService accountService;
    private final RoleService roleService;
    private final PermissionService permissionService;
    private final ClientService clientService;

    private boolean alreadySetup = false;

    @Autowired
    public InitialDataLoader(AccountService accountService, RoleService roleService, PermissionService permissionService, ClientService clientService) {
        this.accountService = accountService;
        this.roleService = roleService;
        this.permissionService = permissionService;
        this.clientService = clientService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (alreadySetup)
            return;

        addPermission("can_create_user");
        addPermission("can_update_user");
        addPermission("can_read_user");
        addPermission("can_delete_user");

        List<Role> roleList = new ArrayList<Role>();
        roleList.add(addRole(Role.ROLE_USER, false));
        roleList.add(addRole(Role.ROLE_ADMIN, true));

        if (!accountService.existsByUsername("admin")) {
            Account account = new Account();
            account.setId("aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee");
            account.setFirstName("Admin");
            account.setLastName("S");
            account.setUsername("admin");
            account.setEmail("admin@admin.ru");
            account.setPassword("{bcrypt}" + EncrytedPasswordUtils.encrytePassword("password"));
            account.setAccountNonExpired(true);
            account.setCredentialsNonExpired(true);
            account.setAccountNonLocked(true);
            account.setEnabled(true);
            account.setRoles(roleList);
            accountService.add(account);
        }

        if (!clientService.existsByClientIde("USER_CLIENT_APP")) {
            Client client = new Client();
            String roles = roleService.getAll().stream().map(e -> e.getName()).reduce("", String::concat);
            client.setClientId("USER_CLIENT_APP");
            client.setClientSecret("{bcrypt}" + EncrytedPasswordUtils.encrytePassword("password"));
            client.setResourceIds("USER_CLIENT_RESOURCE,USER_ADMIN_RESOURCE");
            client.setScope(roles);
            client.setAuthorizedGrantTypes("authorization_code,password,refresh_token,implicit");
            client.setWebServerRedirectUri("APP-PFQ-GATEWAY");
            client.setAuthorities(null);
            client.setAccessTokenValidity(900);
            client.setRefreshTokenValidity(3600);
            client.setAdditionalInformation("{}");
            client.setAutoapprove("true");
            clientService.add(client);
        }
    }

    private void addPermission(String name) {
        if (!permissionService.findByName(name).isPresent()) {
            permissionService.add(new Permission(name));
        }
    }

    private Role addRole(String name, boolean isAdmin) {
        if (!roleService.findByName(name).isPresent()) {
            Role role = null;
            if (isAdmin) {
                role = new Role(name, permissionService.getAll());
            } else {
                role = new Role(name);
            }
            return roleService.add(role);
        }
        return null;
    }

}

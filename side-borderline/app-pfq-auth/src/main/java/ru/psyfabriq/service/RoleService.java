package ru.psyfabriq.service;

import ru.psyfabriq.entity.Role;

import java.util.Optional;

public interface RoleService extends ExecutestService<Role, Role> {
    boolean existsByName(String name);

    Optional<Role> findByName(String name);
}

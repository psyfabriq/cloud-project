package ru.psyfabriq.service;

import ru.psyfabriq.entity.Permission;

import java.util.Optional;

public interface PermissionService extends ExecutestService<Permission, Permission> {
    boolean existsByName(String name);

    Optional<Permission> findByName(String name);
}

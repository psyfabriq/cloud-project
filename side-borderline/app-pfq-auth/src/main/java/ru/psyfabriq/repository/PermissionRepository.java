package ru.psyfabriq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.psyfabriq.entity.Permission;
import ru.psyfabriq.entity.Role;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
    Optional<Permission> findByName(String name);
    Boolean existsByName(String name);
}
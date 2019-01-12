package ru.psyfabriq.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.psyfabriq.entity.Permission;
import ru.psyfabriq.repository.PermissionRepository;
import ru.psyfabriq.service.PermissionService;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Autowired
    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Permission add(Permission entity) {
        return permissionRepository.save(entity);
    }

    @Override
    public Permission edit(Permission entity) {
        return permissionRepository.save(entity);
    }

    @Override
    public boolean delete(Permission entity) {
        if (entity != null) {
            String id = entity.getId();
            permissionRepository.delete(entity);
            return permissionRepository.existsById(id);
        } else {
            return false;
        }
    }

    @Override
    public List<Permission> getAll() {
        return permissionRepository.findAll();
    }

    @Override
    public boolean existsByName(String name) {
        return permissionRepository.findByName(name).map(o -> true).orElse(false);
    }

    @Override
    public Optional<Permission> findByName(String name) {
        return permissionRepository.findByName(name);
    }

    @Deprecated
    @Override
    public Optional<Permission> convertDtoToEntity(Permission dto) {
        return Optional.ofNullable(dto);
    }

    @Deprecated
    @Override
    public Optional<Permission> convertEntityToDTO(Permission entity) {
        return Optional.ofNullable(entity);
    }

}

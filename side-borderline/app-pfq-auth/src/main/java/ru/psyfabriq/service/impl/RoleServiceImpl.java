package ru.psyfabriq.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.psyfabriq.entity.Role;
import ru.psyfabriq.repository.RoleRepository;
import ru.psyfabriq.service.RoleService;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role add(Role entity) {
        return roleRepository.save(entity);
    }

    @Override
    public Role edit(Role entity) {
        return roleRepository.save(entity);
    }

    @Override
    public boolean delete(Role entity) {
        if (entity != null) {
            String id = entity.getId();
            roleRepository.delete(entity);
            return roleRepository.existsById(id);
        } else {
            return false;
        }
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public boolean existsByName(String name) {
        return roleRepository.findByName(name).map(o -> true).orElse(false);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Optional<Role> convertDtoToEntity(Role dto) {
        return Optional.ofNullable(dto);
    }

    @Override
    public Optional<Role> convertEntityToDTO(Role entity) {
        return Optional.ofNullable(entity);
    }

}

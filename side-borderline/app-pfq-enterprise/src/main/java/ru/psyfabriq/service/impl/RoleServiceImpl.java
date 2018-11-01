package ru.psyfabriq.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.psyfabriq.entity.Category;
import ru.psyfabriq.entity.Role;
import ru.psyfabriq.repository.RoleRepository;
import ru.psyfabriq.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public Role addRole(Role role) {
        Role savedRole = roleRepository.saveAndFlush(role);
        return savedRole;
    }

    @Override
    @Transactional
    public void delete(long id) {
        roleRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Role editRole(Role role) {
        return roleRepository.saveAndFlush(role);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existsByName(String name) {
        return roleRepository.existsByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Role getByName(String name) {
        return roleRepository.findByName(name).get();
    }

    @Transactional
    public Role createRoleIfNotFound(String roleName) {

        Optional<Role> optionalRole = roleRepository.findByName(roleName);

        if (optionalRole.isPresent())
            return optionalRole.get();

        Role role = new Role();
        role.setName(roleName);
        roleRepository.save(role);
        return role;
    }

    Role getBasicRole() {
        return createRoleIfNotFound("ROLE_USER");
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Role> findPaginated(Pageable pageable) {

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Role> list;
        List<Role> roles = getAll();

        if (roles.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, roles.size());
            list = roles.subList(startItem, toIndex);
        }

        Page<Role> rolePage = new PageImpl<Role>(list, PageRequest.of(currentPage, pageSize), roles.size());

        return rolePage;
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existsByID(long id) {
        return roleRepository.existsById(id);
    }

}

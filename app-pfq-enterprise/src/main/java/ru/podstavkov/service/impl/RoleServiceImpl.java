package ru.podstavkov.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ru.podstavkov.entity.Category;
import ru.podstavkov.entity.Role;
import ru.podstavkov.repository.RoleRepository;
import ru.podstavkov.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Role addRole(Role role) {
		Role savedRole = roleRepository.saveAndFlush(role);
		return savedRole;
	}

	@Override
	public void delete(long id) {
		roleRepository.deleteById(id);
	}

	@Override
	public Role editRole(Role role) {
		return roleRepository.saveAndFlush(role);
	}

	@Override
	public List<Role> getAll() {
		 return roleRepository.findAll();
	}

	@Override
	public Boolean existsByName(String name) {
		return roleRepository.existsByName(name);
	}

	@Override
	public Role getByName(String name) {
		return roleRepository.findByName(name).get();
	}
	
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
	public Boolean existsByID(long id) {
		return roleRepository.existsById(id);
	}

}

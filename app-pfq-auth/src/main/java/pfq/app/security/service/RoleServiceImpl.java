package pfq.app.security.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pfq.app.security.model.Role;
import pfq.app.security.repository.RoleRepository;

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
        role.setNotdeleted(true);
        roleRepository.save(role);
        return role;
    }

    Role getBasicRole() {
        return createRoleIfNotFound("ROLE_USER");
    }
    
	
	@Override
	@Transactional(readOnly = true)
	public Boolean existsByID(long id) {
		return roleRepository.existsById(id);
	}

}


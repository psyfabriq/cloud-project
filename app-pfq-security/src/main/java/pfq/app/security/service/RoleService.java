package pfq.app.security.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pfq.app.security.model.Role;



public interface RoleService {
	
	Role addRole(Role role);
	void delete(long id);
	Role editRole(Role role);
	List<Role> getAll();
	Boolean existsByName(String name);
	Boolean existsByID(long id);
	Role getByName(String name);
	Role createRoleIfNotFound(String string);


}

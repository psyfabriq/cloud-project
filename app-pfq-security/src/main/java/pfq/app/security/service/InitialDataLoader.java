package pfq.app.security.service;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import pfq.app.security.model.RoleName;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	private boolean alreadySetup = false;
	
	@Value("${administrative.key:administrative}")
	private String UserAdministrativeKey;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Transactional
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (alreadySetup)
			return;

		roleService.createRoleIfNotFound(RoleName.ROLE_ADMIN.name());
		roleService.createRoleIfNotFound(RoleName.ROLE_USER.name());
		userService.createUserIfNotFound(UserAdministrativeKey);

		/*
		userService.getAll().forEach((v) -> {
			if (v.getCode() == null || v.getCode().isEmpty()) {
				v.setCode(UUID.randomUUID().toString());
				userService.editUser(v);
			}
		});
		*/

	}

}

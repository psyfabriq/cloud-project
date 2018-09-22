package pfq.app.security.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.runner.ReactiveWebApplicationContextRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import pfq.app.security.model.Role;
import pfq.app.security.model.User;
import pfq.app.security.repository.RoleRepository;
import pfq.app.security.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	@Transactional
	public User create(User user) {

		Optional<User> existing = repository.findByUsernameOrEmail(user.getUsername(), user.getEmail());
		existing.ifPresent(it-> {throw new IllegalArgumentException("user already exists: " + it.getUsername());});
		user.setPassword(encoder.encode(user.getPassword()));
		Set<Role> roles = user.getRoles();
		roles.add(roleRepository.findByName("ROLE_USER").get());
		User new_user = repository.save(user);
		log.info("new user has been created: {}", user.getUsername());
		return new_user;
	}

	@Override
	public List<User> getAll() {
		   List<User> list = new ArrayList<>();
		   repository.findAll().forEach(list::add);
		  return list;
	}

	@Override
	@Transactional
	public User editUser(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		return repository.save(user);

	}

	@Override
	public User createUserIfNotFound(String Key) {
		Optional<User> ouser = repository.findByCode(Key);
		if(ouser.isPresent()) {
			return ouser.get();
		}else {
			User new_user = new User("Administrator", "admin", "admin@system.loc", "admin"); 
			new_user.setCode(Key);
			Set<Role> roles = new_user.getRoles();
			roles.add(roleRepository.findByName("ROLE_ADMIN").get());
			return create(new_user);
		}
	}
}

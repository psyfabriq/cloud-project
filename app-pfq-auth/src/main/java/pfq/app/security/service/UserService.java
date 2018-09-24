package pfq.app.security.service;

import java.util.List;

import pfq.app.security.model.User;

public interface UserService {

	User create(User user);
    List<User> getAll();
    User editUser(User user);
	User createUserIfNotFound(String Key);



}

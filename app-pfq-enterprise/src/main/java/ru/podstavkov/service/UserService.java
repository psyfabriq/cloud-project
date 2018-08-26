package ru.podstavkov.service;

import java.util.List;

import ru.podstavkov.dto.UserProfile;
import ru.podstavkov.dto.UserRequest;
import ru.podstavkov.entity.User;

public interface UserService extends ExecutestService<User, UserRequest,  UserProfile> {
	    User addUser(User user);
	    void delete(long id);
	    User getByName(String name);
	    User getByCode(String code);
	    User getByNameOrEmail(String name,  String email);
	    User getByEmail(String email);
	    User editUser(User user);
	    List<User> getAll();
	    Boolean existsByUsername(String username);
	    Boolean existsByEmail(String email);
	    Boolean existsByCode(String code);
}

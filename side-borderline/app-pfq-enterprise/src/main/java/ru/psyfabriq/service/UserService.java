package ru.psyfabriq.service;

import java.util.List;

import ru.psyfabriq.dto.UserProfile;
import ru.psyfabriq.dto.UserRequest;
import ru.psyfabriq.entity.User;

public interface UserService extends ExecutestService<User, UserRequest, UserProfile> {
    User addUser(User user);

    void delete(long id);

    User getByName(String name);

    User getByCode(String code);

    User getByNameOrEmail(String name, String email);

    User getByEmail(String email);

    User editUser(User user);

    List<User> getAll();

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByCode(String code);
}

package ru.psyfabriq.service;

import ru.psyfabriq.AppAccountForm;
import ru.psyfabriq.entity.Account;

public interface AccountService extends ExecutestService<Account, AppAccountForm> {

    Account getByNameOrEmail(String name, String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsById(String id);

    boolean delete(String id);
}

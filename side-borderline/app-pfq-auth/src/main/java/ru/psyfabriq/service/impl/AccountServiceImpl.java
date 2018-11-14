package ru.psyfabriq.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.psyfabriq.AppAccountForm;
import ru.psyfabriq.entity.Account;
import ru.psyfabriq.entity.Role;
import ru.psyfabriq.repository.AccountRepository;
import ru.psyfabriq.repository.RoleRepository;
import ru.psyfabriq.service.AccountService;
import ru.psyfabriq.utils.EncrytedPasswordUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;


    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, RoleRepository roleRepository) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Account getByNameOrEmail(String name, String email) {
        return accountRepository.findByUsernameOrEmail(name, email).map(o -> {
            return o;
        }).orElseThrow(() -> new UsernameNotFoundException("No user found with username or email : " + name));
    }

    @Override
    public boolean existsByUsername(String username) {
        return accountRepository.findByUsername(username).map(o -> true).orElse(false);
    }

    @Override
    public boolean existsByEmail(String email) {
        return accountRepository.findByEmail(email).map(o -> true).orElse(false);
    }

    @Override
    public boolean existsById(String id) {
        return accountRepository.existsById(id);
    }

    private Account createNewAccount() {
        List<Role> roles = new ArrayList<>();
        Optional<Role> or = roleRepository.findByName(Role.ROLE_USER);
        if (or.isPresent()) {
            roles.add(or.get());
        } else {
            Role role = roleRepository.save(new Role(Role.ROLE_USER));
            roles.add(role);
        }
        Account account = Account.builder()
                .roles(roles)
                .build();
        return account;
    }

    @Override
    public Optional<Account> convertDtoToEntity(AppAccountForm dto) {
        Account tmp = accountRepository.findById(dto.getUserId()).map(account -> {
            return account;
        }).orElse(createNewAccount());
        String encrytedPassword = EncrytedPasswordUtils.encrytePassword(dto.getPassword());
        tmp.setEmail(dto.getEmail());
        tmp.setPassword("{bcrypt}" + encrytedPassword);
        tmp.setUsername(dto.getUserName());
        tmp.setFirstName(dto.getFirstName());
        tmp.setLastName(dto.getLastName());
        return Optional.ofNullable(tmp);
    }

    @Override
    public Optional<AppAccountForm> convertEntityToDTO(Account entity) {
        AppAccountForm dto = new AppAccountForm();
        dto.setEmail(entity.getEmail());
        dto.setUserName(entity.getUsername());
        dto.setUserId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        return Optional.ofNullable(dto);
    }

    @Override
    @Transactional
    public Account add(Account entity) {
        return accountRepository.save(entity);
    }

    @Override
    @Transactional
    public Account edit(Account entity) {
        return accountRepository.save(entity);
    }

    @Override
    @Transactional
    public boolean delete(Account entity) {
        if (entity != null) {
            String id = entity.getId();
            accountRepository.delete(entity);
            return accountRepository.existsById(id);
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        return accountRepository.findById(id).map(account -> {
            return delete(account);
        }).orElse(false);
    }

    @Override
    public List<Account> getAll() {
        return StreamSupport.stream(accountRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }
}

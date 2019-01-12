package ru.psyfabriq.service.impl;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.psyfabriq.entity.Account;
import ru.psyfabriq.repository.AccountRepository;
import ru.psyfabriq.utils.annotation.LogBefore;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    Logger log = LogManager.getLogger(CustomUserDetailsService.class);
    private final AccountRepository accountRepository;

    @Autowired
    public CustomUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @LogBefore
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or  email : " + username)
                );
        log.warn(account.toString());
        new AccountStatusUserDetailsChecker().check(account);

        return account;
    }

}

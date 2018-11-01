package ru.psyfabriq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.psyfabriq.entity.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
}

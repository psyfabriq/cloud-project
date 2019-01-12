package ru.psyfabriq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.psyfabriq.entity.Account;

import java.util.Optional;

@Repository
@Transactional
public interface AccountRepository extends CrudRepository<Account, String> {

    Optional<Account> findByUsername(String username);

    Optional<Account> findByEmail(String email);

    Optional<Account> findByUsernameOrEmail(String username, String email);

    boolean existsById(String id);
}

package ru.podstavkov.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.podstavkov.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    List<User> findByIdIn(List<Long> userIds);

    Optional<User> findByUsername(String username);
    
    Optional<User> findByCode(String code);
    
    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    
    Boolean existsByCode(String code);
}

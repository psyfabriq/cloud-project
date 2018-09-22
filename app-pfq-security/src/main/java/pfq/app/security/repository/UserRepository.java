package pfq.app.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pfq.app.security.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    List<User> findByIdIn(List<Long> userIds);

    Optional<User> findByUsername(String username);
    
    Optional<User> findByCode(String code);
    
    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    
    Boolean existsByCode(String code);

}

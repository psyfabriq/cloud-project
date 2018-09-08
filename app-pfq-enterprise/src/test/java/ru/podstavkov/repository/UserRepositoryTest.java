package ru.podstavkov.repository;

import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import ru.podstavkov.entity.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private UserRepository repository;
	
	@Test
	public void findByNameTest() {
		this.entityManager.persist(new User("Admin","admin","admin@email.loc","testpwd"));
		Optional<User> user = this.repository.findByUsernameOrEmail("admin", "admin@local.loc");
		assertTrue(user.isPresent());
	}

}

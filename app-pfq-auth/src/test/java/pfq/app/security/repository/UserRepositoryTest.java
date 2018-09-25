package pfq.app.security.repository;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import pfq.app.security.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	private UserRepository repository;

	@Test
	public void shouldSaveAndFindUserByName() {

		User user = new User();
		user.setName("Tester");
		user.setUsername("name");
		user.setPassword("password");
		user.setEmail("system@email.loc");
		repository.save(user);

		Optional<User> found = repository.findByUsernameOrEmail(user.getUsername(),user.getEmail());
		assertTrue(found.isPresent());
		assertEquals(user.getUsername(), found.get().getUsername());
		assertEquals(user.getPassword(), found.get().getPassword());
	}
}

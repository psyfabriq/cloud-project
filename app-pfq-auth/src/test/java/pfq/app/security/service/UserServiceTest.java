package pfq.app.security.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import pfq.app.security.model.User;
import pfq.app.security.repository.UserRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringRunner.class)
public class UserServiceTest {

	@InjectMocks
	private UserServiceImpl userService;

	@Mock
	private UserRepository repository;

	@Before
	public void setup() {
		initMocks(this);
		
	}

	@Test
	public void shouldCreateUser() {

		User user = new User();
		user.setName("Tester");
		user.setUsername("name");
		user.setPassword("password");
		user.setEmail("system@email.loc");

		userService.create(user);
		verify(repository, times(1)).save(user);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFailWhenUserAlreadyExists() {

		User user = new User();
		user.setName("Tester");
		user.setUsername("name");
		user.setPassword("password");
		user.setEmail("system@email.loc");

		when(repository.findByUsernameOrEmail(user.getUsername(),user.getEmail())).thenReturn(Optional.of(new User()));
		userService.create(user);
	}
}

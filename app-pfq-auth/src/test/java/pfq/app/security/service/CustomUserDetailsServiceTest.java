package pfq.app.security.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import pfq.app.security.model.User;
import pfq.app.security.repository.UserRepository;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringRunner.class)
public class CustomUserDetailsServiceTest {
	@InjectMocks
	private CustomUserDetailsService service;

	@Mock
	private UserRepository repository;

	@Before
	public void setup() {
		initMocks(this);
		
		User user = new User();
		user.setName("Tester");
		user.setUsername("name");
		user.setPassword("password");
		user.setEmail("system@email.loc");
		repository.save(user);
	}

	@Test
	public void shouldLoadByUsernameWhenUserExists() {

		final User user = new User();

		when(repository.findById(any())).thenReturn(Optional.of(user));
		UserDetails loaded = service.loadUserByUsername("name");

		assertEquals(user, loaded);
	}

	@Test(expected = UsernameNotFoundException.class)
	public void shouldFailToLoadByUsernameWhenUserNotExists() {
		service.loadUserByUsername("name");
	}
}
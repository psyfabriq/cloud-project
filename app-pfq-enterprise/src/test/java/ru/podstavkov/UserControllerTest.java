package ru.podstavkov;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import ru.podstavkov.controller.UserController;
import ru.podstavkov.entity.User;
import ru.podstavkov.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {
	private UserController userController;
	private User user = new User("admin", "admin", "admin@email.loc", "adminpass");
	

	
	@Before
	public void setUp() throws Exception{
		UserService mocUserService = mock(UserService.class);
		userController = new UserController(mocUserService);
		when(mocUserService.getByName(user.getName())).thenReturn(user);
	}
	
	@Test
	public void getUserTest() throws Exception{
		//User userActual = userController.
	}

}

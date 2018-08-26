
package ru.podstavkov.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.podstavkov.dto.UserIdentityAvailability;
import ru.podstavkov.dto.UserProfile;
import ru.podstavkov.dto.UserSummary;
import ru.podstavkov.entity.User;
import ru.podstavkov.model.UserPrincipal;
import ru.podstavkov.repository.UserRepository;
import ru.podstavkov.utils.annotation.CurrentUser;
import ru.podstavkov.utils.annotation.LogBefore;
import ru.podstavkov.utils.exeption.ResourceNotFoundException;


@RestController
@RequestMapping("/api")
public class RestUserController {
	
	@Autowired
    private UserRepository userRepository;


    private static final Logger logger = LoggerFactory.getLogger(RestUserController.class);

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('ROLE_USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
        return userSummary;
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/users/{username}")
    @LogBefore
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        
        UserProfile userProfile = new UserProfile(user);
        logger.info(userProfile.toString());

        return userProfile;
    }

}

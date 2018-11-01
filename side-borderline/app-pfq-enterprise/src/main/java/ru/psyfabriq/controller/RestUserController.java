
package ru.psyfabriq.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.psyfabriq.dto.UserIdentityAvailability;
import ru.psyfabriq.dto.UserProfile;
import ru.psyfabriq.dto.UserSummary;
import ru.psyfabriq.entity.User;
import ru.psyfabriq.model.UserPrincipal;
import ru.psyfabriq.repository.UserRepository;
import ru.psyfabriq.utils.annotation.CurrentUser;
import ru.psyfabriq.utils.annotation.LogBefore;
import ru.psyfabriq.utils.exeption.ResourceNotFoundException;


@RestController
@RequestMapping("/api")
public class RestUserController {

    private static final Logger logger = LoggerFactory.getLogger(RestUserController.class);
    @Autowired
    private UserRepository userRepository;

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

package ru.psyfabriq.utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.social.security.SocialUserDetails;
import ru.psyfabriq.entity.Account;
//import ru.psyfabriq.service.impl.SocialUserDetailsImpl;

import java.util.List;

public class SecurityUtil {
    /*
    public static void logInUser(Account account, List<String> roleNames) {
       // SocialUserDetails userDetails = new SocialUserDetailsImpl(account, roleNames);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    */
}

package ru.psyfabriq;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/*
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
*/

@Getter
@Setter
@NoArgsConstructor
public class AppAccountForm {

    private String userId;
    private String email;
    private String userName;
    private String firstName;
    private String lastName;
    private String password;
    private String role;
    private String signInProvider;
    private String providerUserId;

}

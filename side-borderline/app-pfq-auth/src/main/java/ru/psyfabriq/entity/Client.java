package ru.psyfabriq.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue
    private Long Id;
    private String clientId;
    private String secret;
    private String scope = StringUtils.arrayToCommaDelimitedString(new String[]{"openid"});
    private String authorizedGrandTypes = StringUtils.arrayToCommaDelimitedString(new String[]{"authorization_code", "refresh_token", "password"});
    private String authorities = StringUtils.arrayToCommaDelimitedString(new String[]{"ROLE_USER", "ROLE_ADMIN"});
    private String autoApproveScope = StringUtils.arrayToCommaDelimitedString(new String[]{".*"});
}

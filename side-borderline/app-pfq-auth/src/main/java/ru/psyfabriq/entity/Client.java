package ru.psyfabriq.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "oauth_client_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @Column(name = "client_id", columnDefinition = "VARCHAR(60)", nullable = false, unique = true)
    private String clientId;
    @Column(name = "access_token_validity", columnDefinition = "INT(6)")
    private int accessTokenValidity;
    @Column(name = "additional_information", columnDefinition = "VARCHAR(255)")
    private String additionalInformation;
    @Column(name = "authorities", columnDefinition = "VARCHAR(100)")
    private String authorities;
    @Column(name = "authorized_grant_types", columnDefinition = "VARCHAR(255)")
    private String authorizedGrantTypes;
    @Column(name = "autoapprove")
    private String autoapprove;
    @Column(name = "client_secret", columnDefinition = "VARCHAR(255)")
    private String clientSecret;
    @Column(name = "refresh_token_validity", columnDefinition = "INT(6)")
    private int refreshTokenValidity;
    @Column(name = "resource_ids", columnDefinition = "VARCHAR(255)")
    private String resourceIds;
    @Column(name = "scope", columnDefinition = "VARCHAR(250)")
    private String scope;
    @Column(name = "web_server_redirect_uri", columnDefinition = "VARCHAR(255)")
    private String webServerRedirectUri;

}

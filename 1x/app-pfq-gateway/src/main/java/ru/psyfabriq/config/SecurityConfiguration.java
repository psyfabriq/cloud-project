package ru.psyfabriq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import java.util.Optional;

@Configuration
@EnableOAuth2Sso
@EnableResourceServer
@Order(value = 0)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final ResourceServerTokenServices resourceServerTokenServices;
    //private final LoadBalancerClient loadBalancerClient;

    @Value("${myProperties.authContextPath}")
    private String authContextPath;

    @Autowired
    public SecurityConfiguration(ResourceServerTokenServices resourceServerTokenServices) {
        this.resourceServerTokenServices = resourceServerTokenServices;

    }

    @Bean
    @Primary
    public OAuth2ClientContextFilter oauth2ClientContextFilterWithPath() {
        return new Oauth2ClientContextFilterWithPath();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception { // @formatter:off
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/" + authContextPath + "/**", "/login").permitAll()
                .anyRequest().authenticated()
                .and().addFilterAfter(oAuth2AuthenticationProcessingFilter(), AbstractPreAuthenticatedProcessingFilter.class)
                .logout().permitAll().logoutSuccessUrl("/");
    } // @formatter:on


    private OAuth2AuthenticationProcessingFilter oAuth2AuthenticationProcessingFilter() {
        OAuth2AuthenticationProcessingFilter oAuth2AuthenticationProcessingFilter = new OAuth2AuthenticationProcessingFilter();
        oAuth2AuthenticationProcessingFilter.setAuthenticationManager(oauthAuthenticationManager());
        oAuth2AuthenticationProcessingFilter.setStateless(false);

        return oAuth2AuthenticationProcessingFilter;
    }

    private AuthenticationManager oauthAuthenticationManager() {
        OAuth2AuthenticationManager oAuth2AuthenticationManager = new OAuth2AuthenticationManager();
        oAuth2AuthenticationManager.setResourceId("apigateway");
        oAuth2AuthenticationManager.setTokenServices(resourceServerTokenServices);
        oAuth2AuthenticationManager.setClientDetailsService(null);

        return oAuth2AuthenticationManager;
    }
}


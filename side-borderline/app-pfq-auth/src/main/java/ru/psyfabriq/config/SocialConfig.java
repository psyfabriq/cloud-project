package ru.psyfabriq.config;
/*

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import ru.psyfabriq.repository.AccountRepository;

import javax.sql.DataSource;

//@Configuration
//@EnableSocial
public class SocialConfig implements SocialConfigurer {

    private final DataSource dataSource;
    private final AccountRepository accountRepository;

    private boolean autoSignUp = false;

    @Autowired
    public SocialConfig(DataSource dataSource, AccountRepository accountRepository) {
        this.dataSource = dataSource;
        this.accountRepository = accountRepository;
    }

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) {
        try {
            this.autoSignUp = Boolean.parseBoolean(env.getProperty("social.auto-signup"));
        } catch (Exception e) {
            this.autoSignUp = false;
        }
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository usersConnectionRepository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());

        if (autoSignUp) {
            //   ConnectionSignUp connectionSignUp = new ConnectionSignUpImpl(appUserDAO);
            usersConnectionRepository.setConnectionSignUp(null);
        } else {
            usersConnectionRepository.setConnectionSignUp(null);
        }
        return usersConnectionRepository;
    }

    @Bean
    public ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository) {
        return new ConnectController(connectionFactoryLocator, connectionRepository);
    }

}
*/
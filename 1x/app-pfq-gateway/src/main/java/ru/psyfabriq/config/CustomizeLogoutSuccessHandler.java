package ru.psyfabriq.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomizeLogoutSuccessHandler implements LogoutSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${myProperties.authContextPath}")
    private String authContextPath;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        if (authentication != null) {
            logger.warn("Logout Sucessfull with Principal: " + authentication.getName());
            response.setStatus(HttpServletResponse.SC_OK);
            response.sendRedirect("/" + authContextPath + "/exit");
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
            response.sendRedirect("/");
        }
    }

}
package ru.psyfabriq.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

public class PreFilter extends ZuulFilter {


    private static final Logger log = LoggerFactory.getLogger(PreFilter.class);

    @Value("${myProperties.authContextPath}")
    private String authContextPath;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();
        log.warn(String.format("%s request to %s", request.getMethod(), request.getRequestURI()));
        if (request.getRequestURI().equals("/" + authContextPath + "/")) {
            try {
                response.sendRedirect(request.getContextPath() + "/");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

}
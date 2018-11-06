package ru.psyfabriq.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class StatsInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(StatsInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

        String referer = request.getHeader("Referer");

        if (modelAndView != null) {
            modelAndView.addObject("forward", referer != null ? referer : "/");
            logger.info(referer);
        }

        //String header = request.getHeader("Accept");
        //if (header != null && header.contains("text/html")) {
        if (modelAndView != null) {
            //String uri = request.getRequestURI().toString();
            //logger.info(uri);
            //uri = uri.substring(uri.length() - 1).equals("/") ? uri.replace(uri.substring(uri.length() - 1), "")
            //		: uri;

            modelAndView.addObject("duri", "secure");
        }

        //}


        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}

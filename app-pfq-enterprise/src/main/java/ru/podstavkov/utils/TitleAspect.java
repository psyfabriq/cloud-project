package ru.podstavkov.utils;

import java.lang.reflect.Method;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.podstavkov.utils.annotation.PageTitle;

@Aspect
@Component
public class TitleAspect {
	
	private static final Logger log = LoggerFactory.getLogger(TitleAspect.class);
	
    @Autowired
    private Messages message;
    
	@Before(" @annotation(ru.podstavkov.utils.annotation.PageTitle)")
	public void logBefore(JoinPoint joinPoint) throws Throwable {

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		PageTitle pageTitle = method.getAnnotation(PageTitle.class);
				
        Map<String, Object> mode = (Map<String, Object>) joinPoint.getArgs()[0];
		mode.put("page_title", message.get(pageTitle.code()));	

	}

}

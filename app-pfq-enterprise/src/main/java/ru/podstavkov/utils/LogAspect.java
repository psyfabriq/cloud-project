package ru.podstavkov.utils;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
	
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);
    
	@Pointcut("@annotation(ru.podstavkov.utils.annotation.Around)")
	public void getPointcutAroundAnotation(){}

	
    @Before("@annotation(ru.podstavkov.utils.annotation.LogBefore)")
    public void logBefore(JoinPoint joinPoint) throws Throwable {
		log.info("logBefore() is running!");
		log.info("method: " + joinPoint.getSignature().getName());
		log.info("params: " + Arrays.toString(joinPoint.getArgs())); 
    }
    
    @After("@annotation(ru.podstavkov.utils.annotation.LogAfter)")
    public void logAfter(JoinPoint joinPoint) throws Throwable {
		log.info("logAfter() is running!");
		log.info("method: " + joinPoint.getSignature().getName());
		log.info("params: " + Arrays.toString(joinPoint.getArgs()));
    }
    
    @After("@annotation(ru.podstavkov.utils.annotation.LogAfterVariable)")
    public void logAfterVariable(JoinPoint joinPoint) throws Throwable {
		log.info("logAfterVariable() is running!");
		log.info("method: " + joinPoint.getSignature().getName());
		log.info("params: " + Arrays.toString(joinPoint.getArgs()));
    }
    
    @Around("getPointcutAroundAnotation()")
    public Object logAroundMethodsExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        log.info("{} executed in {}ms", joinPoint.getSignature().toShortString(), executionTime);
        System.out.println(executionTime);
        return proceed;
    }
    
}

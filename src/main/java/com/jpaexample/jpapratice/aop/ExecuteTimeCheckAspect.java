package com.jpaexample.jpapratice.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ExecuteTimeCheckAspect {

    @Around("execution(* com.jpaexample.jpapratice.controller.*Controller.*(..))") //컨트롤러로부터 로직처리를 하는시간을 계산하기위해서
    public Object executionTime(ProceedingJoinPoint point) throws Throwable{
        long startTime = System.currentTimeMillis();
        Object proceed = point.proceed();
        long endTime = System.currentTimeMillis();
        log.info("클래스이름: " + point.getSignature().getDeclaringTypeName() + ". 메소드이름: " + point.getSignature().getName() + ". 실행시간 : " + (endTime - startTime) + "ms");
        return proceed;
    }
}

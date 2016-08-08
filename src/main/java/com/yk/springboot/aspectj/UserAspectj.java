package com.yk.springboot.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by yukui on 2016/8/5.
 */
@Aspect
@Component
public class UserAspectj {

    private static final Logger log = LoggerFactory.getLogger(UserAspectj.class);

    @Pointcut("execution(* com.yk.springboot.service..*Service.save*(..))")
    public void recordLog(){

    }

    @Before("recordLog()")
    public  void before(){
        System.out.println("执行save方法之前");
    }

    @Around("recordLog()")
    public void log(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("保存之前");
        joinPoint.proceed();
        System.out.println("保存用户之后");
    }

    @After("recordLog()")
    public void testAfter(){
        System.out.println("执行save方法之后");
    }

    @AfterReturning(value = "recordLog()",
            returning = "user" ,argNames = "user")
    public void printUserName(JoinPoint joinPoint, Object user){
        System.out.println("用户保存后执行");
    }
}

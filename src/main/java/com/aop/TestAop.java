package com.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import javax.swing.*;

/**
 * Created by admin on 2018/12/19.
 */
@Aspect
@Component
public class TestAop {
    @Pointcut("execution(* com.zz.TestController.SayHello(..))")
    public void pointCut(){}

    @Before("pointCut()")//之前
    public void before(JoinPoint joinpoint){
        System.out.println("-----------before---------");
    }

    @After("pointCut()")//之后
    public void after(JoinPoint joinpoint){
        System.out.println("-----------after---------");
    }

    @AfterReturning(value = "pointCut()" , returning = "result")//正常返回
    public void afterReturning(JoinPoint joinpoint, Object result){
        System.out.println("-----------afterReturning---------");
    }

    @AfterThrowing(value = "pointCut()" , throwing = "ex")//异常
    public void afterThrowing(Throwable ex){
        System.out.println("-----------afterThrowing---------");
    }

    @Around(value = "pointCut()")//环绕
    public void around(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("-----------around Before---------");
        jp.proceed();
        System.out.println("-----------around After---------");
//        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
//        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
//        HttpServletRequest request = sra.getRequest();

//        Object proceed = jp.proceed();
    }

}

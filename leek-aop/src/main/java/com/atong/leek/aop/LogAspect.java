package com.atong.leek.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: leek
 * @description: 接口请求时间切面
 * @author: atong
 * @create: 2020-11-06 10:57
 */
@Aspect
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution(public * com.atong.leek.*.*(..))")
    public void logPointCut(){}

    @Around("logPointCut()")
    public Object Around(ProceedingJoinPoint joinPoint)throws Throwable{
        String className = "";
        String methodName = "";
        try {
            className = joinPoint.getTarget().getClass().getName();
            methodName = joinPoint.getSignature().getName();
        }catch (Throwable t){}
        try {
            long start = System.currentTimeMillis();
            Object proceed = joinPoint.proceed();
            long end = System.currentTimeMillis();
            logger.info("请求接口 【{}】，耗时 【{} ms】",className+"."+methodName,(end -start));
            return proceed;
        }catch (Throwable e){
            logger.error("请求接口 【{}】异常",className+"."+methodName,e);
            throw e;
        }
    }
}

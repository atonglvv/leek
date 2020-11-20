package com.atong.leek.demo.aop;

/**
 * @program: leek
 * @description: 日志aop
 * @author: atong
 * @create: 2020-11-18 16:05
 */
//@Component
//@Aspect
public class LogAspecta {
//    private static final Logger logger = LoggerFactory.getLogger(com.atong.leek.aop.LogAspect.class);
//
//    @Pointcut("execution(public * com.atong.leek.demo.controller.*.*(..))")
//    public void logPointCut(){}
//
//    @Around("logPointCut()")
//    public Object around(ProceedingJoinPoint joinPoint)throws Throwable{
//        String className = "";
//        String methodName = "";
//        try {
//            className = joinPoint.getTarget().getClass().getName();
//            methodName = joinPoint.getSignature().getName();
//        }catch (Throwable t){}
//        try {
//            long start = System.currentTimeMillis();
//            Object proceed = joinPoint.proceed();
//            long end = System.currentTimeMillis();
//            logger.info("请求接口 【{}】，耗时 【{} ms】",className+"."+methodName,(end -start));
//            return proceed;
//        }catch (Throwable e){
//            logger.error("请求接口 【{}】异常",className+"."+methodName,e);
//            throw e;
//        }
//    }
}

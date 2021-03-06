package com.nuclibrarybackstage.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author aolish333@gmail.com
 * @date 2018/1/12 15:34
 * User:Lee
 */
@Aspect
@Component
public class HttpAspect {

    private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);

//    @Pointcut("execution(public * com.nuclibrarybackstage.controller.*.*(..))")
    @Pointcut("execution(public * com.nuclibrarybackstage.controller.UerController.*(..))")
    public void log(){}

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        logger.info("请求前");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //url
        logger.info("url={}",request.getRequestURI());

        // method
        logger.info("method={}", request.getMethod());

        //ip
        logger.info("ip={}", request.getRemoteAddr());

        //类方法
        logger.info("class_method={}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());

        //参数
        logger.info("args={}", joinPoint.getArgs());
    }

    @After("log()")
    public void doAfter() {
        logger.info("请求后");
    }

    @AfterReturning(pointcut = "log()",returning = "object")
    public void doAfterReturnning(Object object){
        logger.info("response={}", object.toString());
    }
}

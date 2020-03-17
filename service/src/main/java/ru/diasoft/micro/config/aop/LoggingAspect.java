package ru.diasoft.micro.config.aop;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

    private static Logger logger = LogManager.getLogger();

    @Pointcut("within(@ru.diasoft.micro.config.aop.Loggable *))")
    public void loggableClasses() {
    }
    
    @Pointcut("@annotation(ru.diasoft.micro.config.aop.Loggable)")
    public void loggableMethods() {
    }
    
    @Before("loggableClasses() || loggableMethods()")
    public void logMethodCall(JoinPoint joinPoint) {
        if(logger.isDebugEnabled()) {
            String className = joinPoint.getSignature().getDeclaringTypeName();
            String methodName = joinPoint.getSignature().getName();
            
            logger.debug("Method: {}.{}() has been called with input parameters: {}", className, methodName, Arrays.toString(joinPoint.getArgs()));
        }
    }

    @AfterReturning(pointcut = "loggableClasses() || loggableMethods()", returning = "result")
    public void afterDebug(final JoinPoint joinPoint, final Object result) {
        if(logger.isDebugEnabled()) {
            String className = joinPoint.getSignature().getDeclaringTypeName();
            String methodName = joinPoint.getSignature().getName();
            String value = result != null ? result.toString() : "";
            logger.debug("Method {}.{}() return value : {}", className, methodName, value);
        }
    }

    @AfterThrowing(pointcut = "loggableClasses() || loggableMethods()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        logger.error("An exception has been thrown in {}()", joinPoint.getSignature().getName());
        logger.error("Cause : {}", exception.getCause());
    }

}

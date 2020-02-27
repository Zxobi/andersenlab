package com.andersenlab.bookstorageweb.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class ProfilingAspect {

    @Pointcut("within(com.andersenlab.bookstorageweb.controller.*)")
    public void pointCutControllers() {}

    @Around("pointCutControllers()")
    public Object doProfiling(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        try {
            Object retVal = pjp.proceed();
            return retVal;
        } finally {
            log.debug(
                    "Completed " +
                    pjp.toLongString() +
                    " in " +
                    (System.currentTimeMillis() - startTime) +
                    " ms"
            );
        }
    }

}

package com.project.fyst.global.aop.aspect;

import com.project.fyst.global.aop.trace.TraceStatus;
import com.project.fyst.global.aop.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogTraceAspect {
    private final LogTrace logTrace;

    @Around("execution(* com.project.fyst.domain..*..*(..))")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        TraceStatus status = null;
        String message = joinPoint.getSignature().toShortString();
        status = logTrace.begin(message);

        Object result = joinPoint.proceed();

        logTrace.end(status);

        return result;
    }
}

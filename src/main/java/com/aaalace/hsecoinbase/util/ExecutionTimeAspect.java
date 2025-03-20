package com.aaalace.hsecoinbase.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeAspect {

    private static final String BLUE = "\033[34m";
    private static final String RESET = "\033[0m";

    @Around("@annotation(com.aaalace.hsecoinbase.util.MeasureExecutionTime)")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;
        System.out.printf(BLUE + "Метод " + joinPoint.getSignature().getName() + " выполнился за " + duration + " миллисекунд.\n" + RESET);

        return result;
    }
}
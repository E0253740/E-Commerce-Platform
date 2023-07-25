package com.dbs.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TimerAspect {
    @Around("execution(* com.dbs.service.impl.*.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        // 先记录当前时间
        long start = System.currentTimeMillis();
        Object result = pjp.proceed(); // 调用目标方法
        // 再次记录时间
        long end = System.currentTimeMillis();
        System.out.println("耗时" + (start-end));
        return result;
    }
}

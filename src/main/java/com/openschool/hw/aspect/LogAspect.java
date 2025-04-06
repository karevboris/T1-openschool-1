package com.openschool.hw.aspect;

import com.openschool.hw.aspect.exception.CustomException;
import com.openschool.hw.dto.TaskDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class LogAspect {
    @Before("@annotation(com.openschool.hw.aspect.annotation.Logging)")
    public void logCall(JoinPoint joinPoint) {
        log.info("Called method {}#{} with args {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                joinPoint.getArgs());
    }

    @AfterReturning(value = "@annotation(com.openschool.hw.aspect.annotation.Logging)", returning = "task")
    public void logResult(JoinPoint joinPoint, TaskDto task) {
        log.info("Method {}#{} returned {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                task);
    }

    @AfterReturning(value = "@annotation(com.openschool.hw.aspect.annotation.Logging)", returning = "tasks")
    public void logResult(JoinPoint joinPoint, List<TaskDto> tasks) {
        log.info("Method {}#{} returned {} tasks with ids: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                tasks.size(),
                tasks.stream().map(TaskDto::getId).collect(Collectors.toList()));
    }

    @AfterThrowing(value = "@annotation(com.openschool.hw.aspect.annotation.Logging)", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception) {
        log.error("Method {}#{} threw exception: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                exception.toString());
    }

    @Around("@annotation(com.openschool.hw.aspect.annotation.Logging)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        long startTime = System.currentTimeMillis();
        try {
            result = joinPoint.proceed();
        } catch (Exception ex) {
            throw new CustomException(ex.getMessage());
        } finally {
            long endTime = System.currentTimeMillis();
            log.info("{}#{} method execution time: {} ms",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    endTime - startTime);
        }
        return result;
    }
}

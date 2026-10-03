package com.maya.cbs.core.application.aspects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.time.*;

import static com.maya.cbs.core.domain.aspects.ExecutionWrapper.executeThrowable;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
class SpringSchedulerLoggingAspect {

    private final Clock clock;

    @Around ("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public Object logScheduler (ProceedingJoinPoint joinPoint) throws Throwable {
        logScheduledTaskDetails(joinPoint);

        Instant start = Instant.now(clock);
        Object result;
        try {
            result = executeThrowable(joinPoint::proceed);
        }
        catch (RuntimeException exception) {
            result = "Error: " + exception.getMessage();
        }
        finally {
            Duration duration = Duration.between(start, Instant.now());
            logExecutionDuration(joinPoint, duration);
        }
        return result;
    }

    private void logScheduledTaskDetails (ProceedingJoinPoint joinPoint) {
        log.info("Running = {}", getJobName(joinPoint));
    }

    private void logExecutionDuration (ProceedingJoinPoint joinPoint, Duration duration) {
        log.info("execution of {} took {}ms", getMethodName(joinPoint), duration.toMillis());
    }

    private String getJobName (ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String className = signature.getDeclaringTypeName();
        String methodName = signature.getMethod()
            .getName();
        return "Scheduled task: %s:%s".formatted(className, methodName);
    }

    private String getMethodName (JoinPoint joinPoint) {
        return "Spring Scheduler %s ".formatted(joinPoint.getSignature());
    }
}

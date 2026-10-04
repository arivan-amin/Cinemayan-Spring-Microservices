package com.maya.cbs.core.application.aspects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.*;
import java.util.List;

import static com.maya.cbs.core.domain.aspects.ExecutionWrapper.executeThrowable;
import static com.maya.cbs.core.domain.config.CoreApplicationConfig.BASE_PACKAGE;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
class MeasurePerformanceAspect {

    private final Clock clock;

    @Around ("@annotation(" + BASE_PACKAGE + ".core.domain.aspects.LogExecutionTime)")
    public Object logExecutionTimeOfMethod (ProceedingJoinPoint joinPoint) throws Throwable {
        logMethodNameAndParameters(joinPoint);

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

    private void logMethodNameAndParameters (JoinPoint joinPoint) {
        List<Object> args = List.of(joinPoint.getArgs());
        log.info("Called method: {}, with parameters: {}", getMethodName(joinPoint), args);
    }

    private void logExecutionDuration (ProceedingJoinPoint joinPoint, Duration duration) {
        log.info("Execution of {} took {}ms", getMethodName(joinPoint), duration.toMillis());
    }

    private String getMethodName (JoinPoint joinPoint) {
        return joinPoint.getSignature()
            .toString();
    }
}

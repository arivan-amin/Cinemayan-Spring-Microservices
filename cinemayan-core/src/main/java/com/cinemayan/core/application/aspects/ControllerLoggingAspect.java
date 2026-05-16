package com.cinemayan.core.application.aspects;

import com.cinemayan.core.application.audit.AuditDataExtractor;
import com.cinemayan.core.domain.audit.AuditEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.*;

import static com.cinemayan.core.domain.aspects.ExecutionWrapper.executeThrowable;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
class ControllerLoggingAspect {

    private final AuditDataExtractor dataExtractor;
    private final Clock clock;

    @Around ("""
            @annotation(org.springframework.web.bind.annotation.GetMapping)
            or @annotation(org.springframework.web.bind.annotation.PostMapping)
            or @annotation(org.springframework.web.bind.annotation.PutMapping)
            or @annotation(org.springframework.web.bind.annotation.DeleteMapping)
            or @annotation(org.springframework.web.bind.annotation.PatchMapping)
        """)
    public Object logEndpoint (ProceedingJoinPoint joinPoint) throws Throwable {
        logIncomingRequestDetails(joinPoint);

        Instant start = Instant.now(clock);
        Object result = null;
        Throwable caughtException = null;

        try {
            result = executeThrowable(joinPoint::proceed);
        }
        catch (RuntimeException exception) {
            caughtException = exception;
            result = "Error: %s".formatted(exception.getMessage());
        }
        finally {
            Duration duration = Duration.between(start, Instant.now());
            logExecutionDuration(joinPoint, duration);
            extractAuditEventDetailsAndLog(joinPoint, result, duration);
        }

        if (caughtException != null) {
            throw caughtException;
        }

        return result;
    }

    private void logIncomingRequestDetails (ProceedingJoinPoint joinPoint) {
        log.info("Incoming request to: {}, with parameters: {}", joinPoint.getSignature(),
            dataExtractor.maskMethodParameters(joinPoint));
    }

    private void logExecutionDuration (ProceedingJoinPoint joinPoint, Duration duration) {
        log.info("Execution of {} took {}ms", getMethodName(joinPoint), duration.toMillis());
    }

    private void extractAuditEventDetailsAndLog (ProceedingJoinPoint joinPoint, Object result,
                                                 Duration duration) {
        AuditEvent event = dataExtractor.extractAuditData(joinPoint, result, duration);
        log.info("Captured API AuditEvent = {}", event);
    }

    private String getMethodName (JoinPoint joinPoint) {
        return "Controller endpoint %s ".formatted(joinPoint.getSignature());
    }
}

package com.cinemayan.core.application.audit;

import com.cinemayan.core.domain.audit.AuditEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.time.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Slf4j
public class AuditDataExtractor {

    private final String serviceName;
    private final Clock clock;
    private final SensitiveDataMasker sensitiveDataMasker;

    public AuditEvent extractAuditData (ProceedingJoinPoint joinPoint, Object result,
                                        Duration duration) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String requestURL = extractRequestUrl(method);
        String requestAnnotation = extractRequestAnnotation(method);
        String parameters = maskMethodParameters(joinPoint);
        String response = getMethodReturnType(result);

        return AuditEvent.builder()
            .serviceName(serviceName)
            .location(requestURL)
            .action(requestAnnotation)
            .data(parameters)
            .recordedAt(Instant.now(clock))
            .duration(duration)
            .response(response)
            .build();
    }

    private String extractRequestUrl (Method method) {
        if (method.isAnnotationPresent(GetMapping.class)) {
            return extractUrl(method.getAnnotation(GetMapping.class)
                .value());
        }
        else if (method.isAnnotationPresent(PostMapping.class)) {
            return extractUrl(method.getAnnotation(PostMapping.class)
                .value());
        }
        else if (method.isAnnotationPresent(PutMapping.class)) {
            return extractUrl(method.getAnnotation(PutMapping.class)
                .value());
        }
        else if (method.isAnnotationPresent(DeleteMapping.class)) {
            return extractUrl(method.getAnnotation(DeleteMapping.class)
                .value());
        }
        else if (method.isAnnotationPresent(PatchMapping.class)) {
            return extractUrl(method.getAnnotation(PatchMapping.class)
                .value());
        }
        return "";
    }

    private String extractRequestAnnotation (AnnotatedElement method) {
        if (method.isAnnotationPresent(GetMapping.class)) {
            return "Read";
        }
        else if (method.isAnnotationPresent(PostMapping.class)) {
            return "Create";
        }
        else if (method.isAnnotationPresent(PutMapping.class)) {
            return "Update";
        }
        else if (method.isAnnotationPresent(DeleteMapping.class)) {
            return "Delete";
        }
        else if (method.isAnnotationPresent(PatchMapping.class)) {
            return "Patch";
        }
        return "";
    }

    public String maskMethodParameters (ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        String[] paramNames = signature.getParameterNames();

        return IntStream.range(0, args.length)
            .mapToObj(i -> {
                String paramName = resolveParameterName(paramNames, i);
                String masked = sensitiveDataMasker.maskParameterIfSensitive(paramName, args[i]);
                return "%s=%s".formatted(paramName, masked);
            })
            .collect(Collectors.joining(", "));
    }

    private static String getMethodReturnType (Object result) {
        return result == null ? "Void" : result.toString();
    }

    private String extractUrl (String... paths) {
        if (paths != null && paths.length > 0) {
            return String.join(", ", paths);
        }
        return "Unknown URL";
    }

    private String resolveParameterName (String[] paramNames, int index) {
        if (isValidIndex(paramNames, index)) {
            return paramNames[index];
        }
        return "arg%d".formatted(index);
    }

    private static boolean isValidIndex (String[] paramNames, int index) {
        return paramNames != null && index < paramNames.length;
    }
}

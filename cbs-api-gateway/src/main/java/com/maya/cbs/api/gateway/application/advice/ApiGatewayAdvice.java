package com.maya.cbs.api.gateway.application.advice;

import com.maya.cbs.core.application.advice.ProblemDetailFactory;
import com.maya.cbs.core.application.advice.ProblemDetailParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ProblemDetail;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.resource.NoResourceFoundException;

import static com.maya.cbs.core.application.advice.ProblemDetailCategories.RESOURCE_NOT_FOUND;
import static com.maya.cbs.core.application.advice.ProblemDetailExceptionUrls.REACTIVE_RESOURCE_NOT_FOUND_EXCEPTION_URL;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public final class ApiGatewayAdvice {

    private final ProblemDetailFactory factory;

    @ExceptionHandler (NoResourceFoundException.class)
    public ProblemDetail handleResourceNotFound (NoResourceFoundException exception,
                                                 ServerHttpRequest request) {

        log.warn("Resource not found: method={}, uri={}, client={}, message={}",
            request.getMethod(), request.getURI(), request.getRemoteAddress(),
            exception.getMessage());

        return factory.build(ProblemDetailParams.builder()
            .status(NOT_FOUND)
            .title("Resource Not Found")
            .detail("Requested Resource: %s, not found".formatted(request.getURI()))
            .category(RESOURCE_NOT_FOUND)
            .docUrl(REACTIVE_RESOURCE_NOT_FOUND_EXCEPTION_URL)
            .build());
    }
}

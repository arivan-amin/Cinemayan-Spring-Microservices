package com.cinemayan.core.application.advice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;

import static com.cinemayan.core.application.advice.ProblemDetailCategories.CONCURRENCY_CONFLICT;
import static com.cinemayan.core.application.advice.ProblemDetailCategories.INTERNAL_ERROR;
import static com.cinemayan.core.application.advice.ProblemDetailExceptionUrls.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public final class ServerControllerAdvices {

    private final ProblemDetailFactory factory;

    @ExceptionHandler (AsyncRequestTimeoutException.class)
    ProblemDetail handleAsyncRequestTimeoutException (AsyncRequestTimeoutException exception,
                                                      HttpServletRequest request) {
        log.error("Async request timed out, server failed to process request in time: method={}, " +
                "uri={}, client={}", request.getMethod(), request.getRequestURI(),
            request.getRemoteAddr(), exception);

        return factory.build(ProblemDetailParams.builder()
            .status(SERVICE_UNAVAILABLE)
            .title("Async Request Timed Out")
            .detail("Server failed to process request in time")
            .category(INTERNAL_ERROR)
            .docUrl(ASYNC_REQUEST_TIMEOUT_EXCEPTION_URL)
            .build());
    }

    @ExceptionHandler (OptimisticLockingFailureException.class)
    ProblemDetail handleOptimisticLockingFailureException (
        OptimisticLockingFailureException exception, HttpServletRequest request) {
        log.error(
            "Optimistic locking conflict, concurrent modification detected: method={}, uri={}, " +
                "client={}, reason={}", request.getMethod(), request.getRequestURI(),
            request.getRemoteAddr(), exception.getMessage(), exception);

        return factory.build(ProblemDetailParams.builder()
            .status(CONFLICT)
            .title("Optimistic Locking Conflict")
            .detail("Concurrent modification detected: %s".formatted(exception.getMessage()))
            .category(CONCURRENCY_CONFLICT)
            .docUrl(OPTIMISTIC_LOCKING_FAILURE_EXCEPTION_URL)
            .build());
    }

    @ExceptionHandler (Exception.class)
    ProblemDetail handleGeneralExceptions (Exception exception) {
        log.error("Unhandled Exception occurred", exception);

        return factory.build(ProblemDetailParams.builder()
            .status(INTERNAL_SERVER_ERROR)
            .title("Unhandled Exception")
            .detail("Unhandled internal exception occurred")
            .category(INTERNAL_ERROR)
            .docUrl(EXCEPTION_URL)
            .build());
    }
}

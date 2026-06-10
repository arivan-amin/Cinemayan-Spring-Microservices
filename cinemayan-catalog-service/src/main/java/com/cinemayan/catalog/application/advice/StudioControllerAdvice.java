package com.cinemayan.catalog.application.advice;

import com.cinemayan.catalog.domain.studio.exception.StudioAlreadyExistsException;
import com.cinemayan.catalog.domain.studio.exception.StudioNotFoundException;
import com.cinemayan.core.application.advice.ProblemDetailFactory;
import com.cinemayan.core.application.advice.ProblemDetailParams;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.cinemayan.core.application.advice.ProblemDetailCategories.RESOURCE_CONFLICT;
import static com.cinemayan.core.application.advice.ProblemDetailCategories.RESOURCE_NOT_FOUND;
import static com.cinemayan.core.application.advice.ProblemDetailExceptionUrls.RUNTIME_EXCEPTION_URL;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public final class StudioControllerAdvice {

    private final ProblemDetailFactory factory;

    @ExceptionHandler (StudioNotFoundException.class)
    ProblemDetail handleStudioNotFound (StudioNotFoundException exception,
                                        HttpServletRequest request) {
        log.warn("Studio not found Exception [studioID = {}, errorCode = {}, URI = {}]",
            exception.getStudioId(), exception.getErrorCode(), request.getRequestURI());

        return factory.build(ProblemDetailParams.builder()
            .status(NOT_FOUND)
            .title("Resource Not Found")
            .detail("Studio with ID: %s, Not Found".formatted(exception.getStudioId()))
            .category(RESOURCE_NOT_FOUND)
            .docUrl(RUNTIME_EXCEPTION_URL)
            .build());
    }

    @ExceptionHandler (StudioAlreadyExistsException.class)
    ProblemDetail handleStudioAlreadyExists (StudioAlreadyExistsException exception,
                                             HttpServletRequest request) {
        log.warn("Studio already exists [name = {}, errorCode = {}, URI = {}]",
            exception.getStudioName(), exception.getErrorCode(), request.getRequestURI());

        return factory.build(ProblemDetailParams.builder()
            .status(CONFLICT)
            .title("Resource Conflict")
            .detail("Studio with name '%s' already exists".formatted(exception.getStudioName()))
            .category(RESOURCE_CONFLICT)
            .docUrl(RUNTIME_EXCEPTION_URL)
            .build());
    }
}

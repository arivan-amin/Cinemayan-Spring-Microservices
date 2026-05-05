package com.cinemayan.core.application.advice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

import static com.cinemayan.core.application.advice.ProblemDetailCategories.*;
import static com.cinemayan.core.application.advice.ProblemDetailExceptionUrls.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public final class ClientErrorControllerAdvices {

    private final ProblemDetailFactory factory;

    @ExceptionHandler (MethodArgumentTypeMismatchException.class)
    ProblemDetail handleMethodArgumentTypeMismatchException (
        MethodArgumentTypeMismatchException exception, HttpServletRequest request) {
        log.warn("Method argument type mismatch: method={}, uri={}, client={}, param={}, " +
                "providedValue={}, expectedType={}", request.getMethod(), request.getRequestURI(),
            request.getRemoteAddr(), exception.getName(), exception.getValue(),
            exception.getRequiredType());

        return factory.build(ProblemDetailParams.builder()
            .status(BAD_REQUEST)
            .title("Bad request, Method Argument Type Mismatch")
            .detail(
                "Type mismatch on parameter: %s, provided Value: %s, expected Type: %s".formatted(
                    exception.getName(), exception.getValue(), exception.getRequiredType()))
            .category(BAD_OR_UNREADABLE_REQUEST)
            .docUrl(METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION_URL)
            .build());
    }

    @ExceptionHandler (HttpMediaTypeNotAcceptableException.class)
    ProblemDetail handleHttpMediaTypeNotAcceptableException (
        HttpMediaTypeNotAcceptableException exception, HttpServletRequest request) {
        log.warn("Media type not acceptable: method={}, uri={}, client={}, acceptHeader={}, " +
                "supportedMediaTypes={}", request.getMethod(), request.getRequestURI(),
            request.getRemoteAddr(), request.getHeader("Accept"),
            exception.getSupportedMediaTypes());

        return factory.build(ProblemDetailParams.builder()
            .status(NOT_ACCEPTABLE)
            .title("Bad request, Media Type Not Acceptable")
            .detail("Acceptable media types are: %s".formatted(exception.getSupportedMediaTypes()))
            .category(BAD_OR_UNREADABLE_REQUEST)
            .docUrl(HTTP_MEDIA_TYPE_NOT_ACCEPTABLE_EXCEPTION_URL)
            .build());
    }

    @ExceptionHandler (HttpMediaTypeNotSupportedException.class)
    ProblemDetail handleHttpMediaTypeNotSupportedException (
        HttpMediaTypeNotSupportedException exception, HttpServletRequest request) {
        log.warn("Unsupported media type: method={}, uri={}, client={}, contentType={}, " +
                "supportedMediaTypes={}", request.getMethod(), request.getRequestURI(),
            request.getRemoteAddr(), exception.getContentType(),
            exception.getSupportedMediaTypes());

        return factory.build(ProblemDetailParams.builder()
            .status(UNSUPPORTED_MEDIA_TYPE)
            .title("Bad request, Media Type Not Supported")
            .detail("Supported Media types are: %s".formatted(exception.getSupportedMediaTypes()))
            .category(BAD_OR_UNREADABLE_REQUEST)
            .docUrl(HTTP_MEDIA_TYPE_NOT_SUPPORTED_EXCEPTION_URL)
            .build());
    }

    @ExceptionHandler (HttpRequestMethodNotSupportedException.class)
    ProblemDetail handleHttpRequestMethodNotSupportedException (
        HttpRequestMethodNotSupportedException exception, HttpServletRequest request) {
        log.warn("HTTP method not allowed: method={}, uri={}, client={}, supportedMethods={}",
            request.getMethod(), request.getRequestURI(), request.getRemoteAddr(),
            exception.getSupportedHttpMethods());

        return factory.build(ProblemDetailParams.builder()
            .status(METHOD_NOT_ALLOWED)
            .title("Bad Request, Method Not Allowed")
            .detail("Request doesn't allow method: %s".formatted(request.getMethod()))
            .category(BAD_OR_UNREADABLE_REQUEST)
            .docUrl(HTTP_REQUEST_METHOD_NOT_SUPPORTED_EXCEPTION_URL)
            .build());
    }

    @ExceptionHandler (ConstraintViolationException.class)
    ProblemDetail handleConstraintViolation (ConstraintViolationException exception,
                                             HttpServletRequest request) {
        List<String> violations = exception.getConstraintViolations()
            .stream()
            .map(violation -> violation.getPropertyPath() + ":" + violation.getMessage())
            .toList();

        log.warn("Constraint violation: method={}, uri={}, client={}, violations={}",
            request.getMethod(), request.getRequestURI(), request.getRemoteAddr(), violations);

        return factory.build(ProblemDetailParams.builder()
            .status(BAD_REQUEST)
            .title("Bad request, Constraint Violation")
            .detail("Request violates following constraints: %s".formatted(violations))
            .category(VALIDATION_ERROR)
            .docUrl(CONSTRAINT_VIOLATION_EXCEPTION_URL)
            .build());
    }

    @ExceptionHandler (MissingPathVariableException.class)
    ProblemDetail handleMissingPathVariable (MissingPathVariableException exception,
                                             HttpServletRequest request) {
        log.warn("Bad request, Missing required path variables [uri={}, variable={}]",
            request.getRequestURI(), exception.getVariableName(), exception);

        return factory.build(ProblemDetailParams.builder()
            .status(BAD_REQUEST)
            .title("Bad request, Missing Path Variable")
            .detail("Missing required path variable: %s".formatted(exception.getVariableName()))
            .category(MISSING_PARAMETER)
            .docUrl(MISSING_PATH_VARIABLE_EXCEPTION_URL)
            .build());
    }

    @ExceptionHandler (HttpMessageNotReadableException.class)
    ProblemDetail handleMissingRequestBody (HttpMessageNotReadableException exception,
                                            HttpServletRequest request) {
        log.warn(
            "Bad request, Malformed or missing request body [method = {},uri = {},contentType = " +
                "{},client = {},reason = {}", request.getMethod(), request.getRequestURI(),
            request.getContentType(), request.getRemoteAddr(), exception.getMessage());

        return factory.build(ProblemDetailParams.builder()
            .status(BAD_REQUEST)
            .title("Bad Request, Missing Required Request Body")
            .detail("Required request body is missing or unreadable for: %s".formatted(
                exception.getMessage()))
            .category(MISSING_PARAMETER)
            .docUrl(HTTP_MESSAGE_NOT_READABLE_EXCEPTION_URL)
            .build());
    }

    @ExceptionHandler (MethodArgumentNotValidException.class)
    ProblemDetail handleMethodArgumentNotValid (MethodArgumentNotValidException exception,
                                                HttpServletRequest request) {
        List<String> violations = exception.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> "%s:%s".formatted(error.getField(), error.getDefaultMessage()))
            .toList();
        log.warn(
            "Bad Request, Validation failed [method = {},uri = {}, client = {}, validations = {}]",
            request.getMethod(), request.getRequestURI(), request.getRemoteAddr(), violations);

        return factory.build(ProblemDetailParams.builder()
            .status(BAD_REQUEST)
            .title("Bad Request, Validation failed")
            .detail("Validation failed for %s".formatted(violations))
            .category(MISSING_PARAMETER)
            .docUrl(METHOD_ARGUMENT_NOT_VALID_EXCEPTION_URL)
            .build());
    }

    @ExceptionHandler (MissingServletRequestParameterException.class)
    ProblemDetail handleMissingParameterException (
        MissingServletRequestParameterException exception, HttpServletRequest request) {
        log.warn(
            "Missing required request parameter: [method = {}, uri = {}, client = {}, paramName =" +
                " {}, " + "paramType = {}]", request.getMethod(), request.getRequestURI(),
            request.getRemoteAddr(), exception.getParameterName(), exception.getParameterType());

        return factory.build(ProblemDetailParams.builder()
            .status(BAD_REQUEST)
            .title("Bad Request, Missing Parameter")
            .detail("Request is missing parameter %s of type: %s".formatted(
                exception.getParameterName(), exception.getParameterType()))
            .category(MISSING_PARAMETER)
            .docUrl(MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION_URL)
            .build());
    }

    @ExceptionHandler (NoResourceFoundException.class)
    ProblemDetail handleResourceNotFound (NoResourceFoundException exception,
                                          HttpServletRequest request) {
        log.warn("Resource not found: method={}, uri={}, client={}, resourcePath={}",
            request.getMethod(), request.getRequestURI(), request.getRemoteAddr(),
            exception.getResourcePath());

        return factory.build(ProblemDetailParams.builder()
            .status(NOT_FOUND)
            .title("Resource Not Found")
            .detail("Requested Resource: %s, not found".formatted(exception.getResourcePath()))
            .category(RESOURCE_NOT_FOUND)
            .docUrl(RESOURCE_NOT_FOUND_EXCEPTION_URL)
            .build());
    }
}

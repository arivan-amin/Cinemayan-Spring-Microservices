package com.cinemayan.catalog.domain.studio.exception;

import lombok.Getter;

@Getter
public class StudioException extends RuntimeException {

    private final StudioErrorCode errorCode;

    protected StudioException (String message, StudioErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    protected StudioException (String message, Throwable cause, StudioErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}

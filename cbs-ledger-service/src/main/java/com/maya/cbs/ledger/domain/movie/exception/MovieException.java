package com.maya.cbs.ledger.domain.movie.exception;

import lombok.Getter;

@Getter
public class MovieException extends RuntimeException {

    private final MovieErrorCode errorCode;

    protected MovieException (String message, MovieErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    protected MovieException (String message, Throwable cause, MovieErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}

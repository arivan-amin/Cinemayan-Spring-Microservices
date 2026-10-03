package com.maya.cbs.ledger.domain.movie.exception;

import lombok.Getter;

@Getter
public class MovieAlreadyExistsException extends MovieException {

    private final String movieTitle;

    public MovieAlreadyExistsException (String movieTitle) {
        super("Movie with title: %s already exists".formatted(movieTitle),
            MovieErrorCode.MOVIE_ALREADY_EXISTS);
        this.movieTitle = movieTitle;
    }

    public MovieAlreadyExistsException (String movieTitle, Throwable cause) {
        super("Movie with title: %s already exists".formatted(movieTitle), cause,
            MovieErrorCode.MOVIE_ALREADY_EXISTS);
        this.movieTitle = movieTitle;
    }
}

package com.maya.cbs.ledger.domain.movie.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class MovieNotFoundException extends MovieException {

    private final UUID movieId;

    public MovieNotFoundException (UUID movieId) {
        super("Movie with ID: %s not found".formatted(movieId), MovieErrorCode.MOVIE_NOT_FOUND);
        this.movieId = movieId;
    }

    public MovieNotFoundException (UUID movieId, Throwable cause) {
        super("Movie with ID: %s not found".formatted(movieId), cause,
            MovieErrorCode.MOVIE_NOT_FOUND);
        this.movieId = movieId;
    }
}

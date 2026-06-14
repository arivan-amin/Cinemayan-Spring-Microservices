package com.cinemayan.catalog.application.config.cache;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor (access = AccessLevel.PRIVATE)
public final class MovieCaches {

    public static final String GET_ALL_MOVIES = "moviesCache";
    public static final String GET_MOVIE_BY_ID = "movieByIdCache";
}

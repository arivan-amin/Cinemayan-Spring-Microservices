package com.cinemayan.catalog.application.urls;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor (access = AccessLevel.PRIVATE)
public final class MovieApiURLs {

    public static final String MOVIES = "/v1/movies";
    public static final String MOVIE_BY_ID = "/v1/movies/{id}";

    public static final String GET_MOVIES_URL = CatalogApiURLs.PROTECTED_API + MOVIES;
    public static final String GET_MOVIE_BY_ID_URL = CatalogApiURLs.PROTECTED_API + MOVIE_BY_ID;
    public static final String CREATE_MOVIE_URL = CatalogApiURLs.PROTECTED_API + MOVIES;
    public static final String UPDATE_MOVIE_URL = CatalogApiURLs.PROTECTED_API + MOVIE_BY_ID;
    public static final String DELETE_MOVIE_URL = CatalogApiURLs.PROTECTED_API + MOVIE_BY_ID;
}

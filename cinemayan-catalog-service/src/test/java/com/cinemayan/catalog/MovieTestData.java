package com.cinemayan.catalog;

import com.cinemayan.catalog.domain.movie.entity.Movie;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor (access = AccessLevel.PRIVATE)
public final class MovieTestData {

    public static List<Movie> moviesList () {
        return List.of(withTitle("Ice age"), withTitle("Non stop"), withTitle("Troy"));
    }

    public static Movie withTitle (String title) {
        Movie entity = new Movie();
        return entity;
    }

    public static Movie withDefaultTitle () {
        return withTitle("Gladiator");
    }
}

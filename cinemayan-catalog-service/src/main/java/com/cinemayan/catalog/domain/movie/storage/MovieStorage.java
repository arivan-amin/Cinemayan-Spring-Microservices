package com.cinemayan.catalog.domain.movie.storage;

import com.cinemayan.catalog.domain.movie.entity.Movie;
import com.cinemayan.core.domain.pagination.PaginatedResponse;
import com.cinemayan.core.domain.pagination.PaginationCriteria;

import java.util.Optional;
import java.util.UUID;

public interface MovieStorage {

    PaginatedResponse<Movie> findAll (MovieSearchCriteria params, PaginationCriteria criteria);

    Optional<Movie> findById (UUID id);

    Optional<Movie> findByTitle (String title);

    UUID create (Movie student);

    void update (Movie student);

    void delete (UUID id);
}

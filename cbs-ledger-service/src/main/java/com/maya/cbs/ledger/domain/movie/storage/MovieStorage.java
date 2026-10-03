package com.maya.cbs.ledger.domain.movie.storage;

import com.maya.cbs.core.domain.pagination.PaginatedResponse;
import com.maya.cbs.core.domain.pagination.PaginationCriteria;
import com.maya.cbs.ledger.domain.movie.entity.Movie;

import java.util.Optional;
import java.util.UUID;

public interface MovieStorage {

    PaginatedResponse<Movie> findAll (MovieSearchCriteria params, PaginationCriteria criteria);

    Optional<Movie> findById (UUID id);

    Optional<Movie> findByTitle (String title);

    UUID create (Movie movie);

    void update (Movie movie);

    void delete (UUID id);
}

package com.maya.cbs.ledger.infrastructure.storage.movie;

import com.maya.cbs.core.domain.pagination.PaginatedResponse;
import com.maya.cbs.core.domain.pagination.PaginationCriteria;
import com.maya.cbs.ledger.domain.movie.entity.Movie;
import com.maya.cbs.ledger.domain.movie.storage.MovieSearchCriteria;
import com.maya.cbs.ledger.domain.movie.storage.MovieStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class MovieJpaStorage implements MovieStorage {

    // private final MovieRepository repository;

    @Transactional (readOnly = true)
    @Override
    public PaginatedResponse<Movie> findAll (MovieSearchCriteria params,
                                             PaginationCriteria criteria) {
        return PaginatedResponse.of(null, null);
    }

    @Transactional (readOnly = true)
    @Override
    public Optional<Movie> findById (UUID id) {
        return Optional.empty();
    }

    @Transactional (readOnly = true)
    @Override
    public Optional<Movie> findByTitle (String title) {
        return Optional.empty();
    }

    @Transactional
    @Override
    public UUID create (Movie movie) {
        return null;
    }

    @Transactional
    @Override
    public void update (Movie movie) {
    }

    @Transactional
    @Override
    public void delete (UUID id) {
    }
}

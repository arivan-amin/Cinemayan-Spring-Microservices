package com.cinemayan.catalog.application.beans;

import com.cinemayan.catalog.domain.movie.storage.MovieStorage;
import com.cinemayan.catalog.infrastructure.storage.movie.MovieJpaStorage;
import com.cinemayan.catalog.infrastructure.storage.movie.MovieRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MovieBeansConfig {

    @Bean
    public MovieStorage movieStorage (MovieRepository repository) {
        return new MovieJpaStorage(repository);
    }
}

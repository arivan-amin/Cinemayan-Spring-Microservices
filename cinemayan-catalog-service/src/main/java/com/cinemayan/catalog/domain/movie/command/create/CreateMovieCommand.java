package com.cinemayan.catalog.domain.movie.command.create;

import com.cinemayan.catalog.domain.movie.entity.Movie;
import com.cinemayan.catalog.domain.movie.storage.MovieStorage;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class CreateMovieCommand {

    private final MovieStorage storage;

    public Output execute (Input input) {
        Movie student = input.movie();
        return new Output(storage.create(student));
    }

    public record Input(
        @NotNull
        Movie movie) {

    }

    public record Output(UUID movieId) {

    }
}

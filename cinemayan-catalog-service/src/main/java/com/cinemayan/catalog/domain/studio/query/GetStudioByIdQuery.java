package com.cinemayan.catalog.domain.studio.query;

import com.cinemayan.catalog.domain.studio.entity.Studio;
import com.cinemayan.catalog.domain.studio.exception.StudioNotFoundException;
import com.cinemayan.catalog.domain.studio.persistence.StudioStorage;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.UUID;

@RequiredArgsConstructor
public class GetStudioByIdQuery {

    private final StudioStorage storage;

    public Output execute (Input input) {
        Studio studio = storage.findById(input.getId())
            .orElseThrow(() -> new StudioNotFoundException(input.getId()));
        return new Output(studio);
    }

    @Value
    public static class Input {

        UUID id;
    }

    @Value
    public static class Output {

        Studio studio;
    }
}

package com.cinemayan.catalog.domain.studio.query;

import com.cinemayan.catalog.domain.studio.entity.Studio;
import com.cinemayan.catalog.domain.studio.exception.StudioNotFoundException;
import com.cinemayan.catalog.domain.studio.persistence.StudioStorage;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class GetStudioByIdQuery {

    private final StudioStorage storage;

    public Output execute (Input input) {
        Studio studio = storage.findById(input.getId())
            .orElseThrow(() -> {
                log.warn("Studio by id = {} not found", input.getId());
                return new StudioNotFoundException(input.getId());
            });
        log.debug("Found studio by id = {}", studio);
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

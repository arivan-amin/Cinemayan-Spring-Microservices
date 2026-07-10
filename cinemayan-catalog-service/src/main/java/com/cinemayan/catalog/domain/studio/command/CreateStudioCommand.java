package com.cinemayan.catalog.domain.studio.command;

import com.cinemayan.catalog.domain.studio.entity.Studio;
import com.cinemayan.catalog.domain.studio.exception.StudioAlreadyExistsException;
import com.cinemayan.catalog.domain.studio.persistence.StudioStorage;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class CreateStudioCommand {

    private final StudioStorage storage;

    public Output execute (Input input) {
        Studio studio = input.getStudio();
        if (doesStudioNameExists(studio)) {
            log.warn("Studio creation rejected, name already exists: name = {}", studio.getName());
            throw new StudioAlreadyExistsException(studio.getName());
        }

        Studio savedStudio = storage.create(studio);
        log.info("Successfully saved studio = {}", savedStudio);
        return new Output(savedStudio);
    }

    private boolean doesStudioNameExists (Studio studio) {
        return storage.findByName(studio.getName())
            .isPresent();
    }

    @Value
    public static class Input {

        Studio studio;
    }

    @Value
    public static class Output {

        Studio studio;
    }
}

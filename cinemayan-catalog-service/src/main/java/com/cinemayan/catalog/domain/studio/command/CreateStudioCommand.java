package com.cinemayan.catalog.domain.studio.command;

import com.cinemayan.catalog.domain.studio.entity.Studio;
import com.cinemayan.catalog.domain.studio.exception.StudioAlreadyExistsException;
import com.cinemayan.catalog.domain.studio.persistence.StudioStorage;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
public class CreateStudioCommand {

    private final StudioStorage storage;

    public Output execute (Input input) {
        Studio studio = input.getStudio();
        if (storage.findByName(studio.getName())
            .isPresent()) {
            throw new StudioAlreadyExistsException(studio.getName());
        }
        Studio savedStudio = storage.create(studio);
        return new Output(savedStudio);
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

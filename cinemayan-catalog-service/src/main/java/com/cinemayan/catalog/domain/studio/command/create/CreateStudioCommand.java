package com.cinemayan.catalog.domain.studio.command.create;

import com.cinemayan.catalog.domain.studio.entity.Studio;
import com.cinemayan.catalog.domain.studio.exception.StudioAlreadyExistsException;
import com.cinemayan.catalog.domain.studio.persistence.StudioStorage;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.UUID;

@RequiredArgsConstructor
public class CreateStudioCommand {

    private final StudioStorage storage;

    public Output execute (Input input) {
        Studio studio = input.getStudio();
        if (storage.findByName(studio.getName())
            .isPresent()) {
            throw new StudioAlreadyExistsException(studio.getName());
        }
        UUID createdStudioId = storage.create(studio);
        return new Output(createdStudioId);
    }

    @Value
    public static class Input {

        Studio studio;
    }

    @Value
    public static class Output {

        UUID id;
    }
}

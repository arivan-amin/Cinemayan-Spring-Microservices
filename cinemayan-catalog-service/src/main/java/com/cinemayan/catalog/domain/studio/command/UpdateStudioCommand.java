package com.cinemayan.catalog.domain.studio.command;

import com.cinemayan.catalog.domain.studio.entity.Studio;
import com.cinemayan.catalog.domain.studio.exception.StudioNotFoundException;
import com.cinemayan.catalog.domain.studio.persistence.StudioStorage;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.UUID;

@RequiredArgsConstructor
public class UpdateStudioCommand {

    private final StudioStorage storage;

    public Output execute (Input input) {
        UUID studioId = input.getStudio()
            .getId()
            .getValue();
        if (storage.findById(studioId)
            .isEmpty()) {
            throw new StudioNotFoundException(studioId);
        }
        Studio updated = storage.update(input.getStudio());
        return new Output(updated);
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

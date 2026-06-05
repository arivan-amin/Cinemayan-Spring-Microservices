package com.cinemayan.catalog.domain.studio.command;

import com.cinemayan.catalog.domain.studio.exception.StudioNotFoundException;
import com.cinemayan.catalog.domain.studio.persistence.StudioStorage;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.UUID;

@RequiredArgsConstructor
public class DeleteStudioCommand {

    private final StudioStorage storage;

    public void execute (Input input) {
        storage.findById(input.getId())
            .orElseThrow(() -> new StudioNotFoundException(input.getId()));
        storage.delete(input.getId());
    }

    @Value
    public static class Input {

        UUID id;
    }
}

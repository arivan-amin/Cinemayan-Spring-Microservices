package com.cinemayan.catalog.domain.studio.command;

import com.cinemayan.catalog.domain.studio.exception.StudioNotFoundException;
import com.cinemayan.catalog.domain.studio.persistence.StudioStorage;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class DeleteStudioCommand {

    private final StudioStorage storage;

    public void execute (Input input) {
        storage.findById(input.getId())
            .orElseThrow(() -> {
                log.warn("Studio deletion rejected, studio id = {} not found", input.getId());
                return new StudioNotFoundException(input.getId());
            });
        storage.delete(input.getId());
    }

    @Value
    public static class Input {

        UUID id;
    }
}

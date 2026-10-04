package com.maya.cbs.ledger.domain.studio.command;

import com.maya.cbs.ledger.domain.studio.entity.Studio;
import com.maya.cbs.ledger.domain.studio.exception.StudioAlreadyExistsException;
import com.maya.cbs.ledger.domain.studio.persistence.StudioStorage;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
public class CreateStudioCommand {

    private final StudioStorage storage;

    @Transactional
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

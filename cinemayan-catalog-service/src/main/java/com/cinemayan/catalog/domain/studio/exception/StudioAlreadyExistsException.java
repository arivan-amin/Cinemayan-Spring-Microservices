package com.cinemayan.catalog.domain.studio.exception;

import lombok.Getter;

@Getter
public class StudioAlreadyExistsException extends StudioException {

    private final String studioName;

    public StudioAlreadyExistsException (String studioName) {
        super("Studio with name: %s already exists".formatted(studioName),
            StudioErrorCode.STUDIO_ALREADY_EXISTS);
        this.studioName = studioName;
    }

    public StudioAlreadyExistsException (String studioName, Throwable cause) {
        super("Studio with name: %s already exists".formatted(studioName), cause,
            StudioErrorCode.STUDIO_ALREADY_EXISTS);
        this.studioName = studioName;
    }
}

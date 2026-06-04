package com.cinemayan.catalog.domain.studio.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class StudioNotFoundException extends StudioException {

    private final UUID studioId;

    public StudioNotFoundException (UUID studioId) {
        super("Studio with ID: %s not found".formatted(studioId), StudioErrorCode.STUDIO_NOT_FOUND);
        this.studioId = studioId;
    }

    public StudioNotFoundException (UUID studioId, Throwable cause) {
        super("Studio with ID: %s not found".formatted(studioId), cause,
            StudioErrorCode.STUDIO_NOT_FOUND);
        this.studioId = studioId;
    }
}

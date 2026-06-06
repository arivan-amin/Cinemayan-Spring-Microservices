package com.cinemayan.catalog.domain.studio.entity;

import com.cinemayan.core.domain.util.DomainId;
import lombok.Value;

import java.util.UUID;

@Value
public class StudioId implements DomainId<UUID> {

    UUID value;

    public static StudioId of (UUID value) {
        return new StudioId(value);
    }
}

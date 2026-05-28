package com.cinemayan.catalog.application.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor (access = AccessLevel.PRIVATE)
public final class StudioCaches {

    public static final String GET_ALL_STUDIOS = "studiosCache";
    public static final String GET_STUDIO_BY_ID = "studioByIdCache";
}

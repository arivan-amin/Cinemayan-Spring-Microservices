package com.cinemayan.catalog.application.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor (access = AccessLevel.PRIVATE)
public final class StudioApiURLs {

    public static final String STUDIOS = "/v1/studios";
    public static final String STUDIO_BY_ID = "/v1/studios/{id}";

    public static final String GET_STUDIOS_URL = CatalogApiURLs.PROTECTED_API + STUDIOS;
    public static final String GET_STUDIO_BY_ID_URL = CatalogApiURLs.PROTECTED_API + STUDIO_BY_ID;
    public static final String CREATE_STUDIO_URL = CatalogApiURLs.PROTECTED_API + STUDIOS;
    public static final String UPDATE_STUDIO_URL = CatalogApiURLs.PROTECTED_API + STUDIO_BY_ID;
    public static final String DELETE_STUDIO_URL = CatalogApiURLs.PROTECTED_API + STUDIO_BY_ID;
}

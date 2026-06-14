package com.cinemayan.catalog.application.urls;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor (access = AccessLevel.PRIVATE)
public final class CatalogApiURLs {

    public static final String PUBLIC_API = "/catalogs/public";
    public static final String PROTECTED_API = "/catalogs/protected";
}

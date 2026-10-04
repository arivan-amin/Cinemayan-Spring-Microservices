package com.maya.cbs.core.domain.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor (access = AccessLevel.PRIVATE)
public final class CoreApplicationConfig {

    public static final String BASE_PACKAGE = "com.maya.cbs";

    public static final String LIQUIBASE_CHANGELOG_PATH =
        "classpath:db/changelog/changelog-master.xml";
}

package com.maya.cbs.core.application.config;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties (prefix = "maya.cbs.openapi.server.url")
public record OpenApiServerProperties(
    @NotNull
    String url) {

}

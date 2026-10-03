package com.maya.cbs.core.application.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties (prefix = "cinemayan.openapi.server.url")
public record OpenApiServerProperties(String url) {

}

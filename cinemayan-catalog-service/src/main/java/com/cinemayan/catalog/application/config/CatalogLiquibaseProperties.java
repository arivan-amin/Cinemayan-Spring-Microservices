package com.cinemayan.catalog.application.config;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties (prefix = "cinemayan.catalog.liquibase")
public record CatalogLiquibaseProperties(
    @NotBlank
    String url,

    @NotBlank
    String username,

    @NotBlank
    String password) {

}

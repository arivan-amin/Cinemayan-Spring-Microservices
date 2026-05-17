package com.cinemayan.apigateway.application.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties (prefix = "cinemayan.api.gateway.redis")
public record RedisProperties(
    @NotBlank
    String host,

    @Positive
    int port) {

}

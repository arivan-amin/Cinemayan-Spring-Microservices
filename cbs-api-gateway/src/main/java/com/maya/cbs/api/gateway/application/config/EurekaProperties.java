package com.maya.cbs.api.gateway.application.config;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties (prefix = "maya.cbs.api.gateway.route.eureka")
public record EurekaProperties(
    @NotBlank
    String host,

    @NotBlank
    String port) {

}

package com.cinemayan.apigateway.application.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Value
@Validated
@ConfigurationProperties (prefix = "cinemayan.api.gateway.route.eureka")
public class EurekaProperties {

    @NotBlank
    String host;

    @NotBlank
    String port;
}

package com.cinemayan.apigateway.application.config;

import com.cinemayan.apigateway.domain.ServiceRoute;
import jakarta.validation.constraints.NotEmpty;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Value
@Validated
@ConfigurationProperties (prefix = "cinemayan.api.gateway.service")
public class RouteProperties {

    @NotEmpty
    List<ServiceRoute> routes = new ArrayList<>();
}

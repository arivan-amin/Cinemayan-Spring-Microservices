package com.maya.cbs.api.gateway.application.config;

import com.maya.cbs.api.gateway.domain.ServiceRoute;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@ConfigurationProperties (prefix = "cinemayan.api.gateway.service")
public record RouteProperties(
    @NotEmpty
    List<ServiceRoute> routes) {

}

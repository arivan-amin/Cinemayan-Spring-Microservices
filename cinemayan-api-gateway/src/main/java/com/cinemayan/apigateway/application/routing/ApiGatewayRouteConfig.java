package com.cinemayan.apigateway.application.routing;

import com.cinemayan.apigateway.application.config.EurekaProperties;
import com.cinemayan.apigateway.application.config.RouteProperties;
import com.cinemayan.apigateway.domain.RouteCreator;
import com.cinemayan.apigateway.domain.ServiceRoute;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;
import java.util.function.Function;

@Configuration
@RequiredArgsConstructor
@Slf4j
class ApiGatewayRouteConfig {

    private final RouteCreator routeCreator;
    private final RouteProperties routeProperties;
    private final EurekaProperties eurekaProperties;

    @Bean
    public RouteLocator routeLocator (RouteLocatorBuilder builder) {
        log.info("Service route Properties fetched from config file = {}", routeProperties);
        log.info("EurekaProperties fetched from config file = {}", eurekaProperties);
        RouteLocatorBuilder.Builder routes = builder.routes()
            .route(getDiscoveryServerRoute())
            .route(getDiscoveryServerStaticResourcesRoute());

        routeProperties.routes()
            .forEach(service -> createRoutesForService(routes, service));

        return routes.build();
    }

    private Function<PredicateSpec, Buildable<Route>> getDiscoveryServerRoute () {
        return r -> r.path("/eureka/web")
            .filters(filter -> filter.setPath("/"))
            .uri(getEurekaUrl());
    }

    private Function<PredicateSpec, Buildable<Route>> getDiscoveryServerStaticResourcesRoute () {
        return r -> r.path("/eureka/**")
            .uri(getEurekaUrl());
    }

    private void createRoutesForService (RouteLocatorBuilder.Builder routes, ServiceRoute service) {
        String serviceKey = service.getName()
            .toLowerCase(Locale.ENGLISH);
        String apiRouteId = "%s-api".formatted(serviceKey);
        String apiDocRouteId = "%s-api-doc".formatted(serviceKey);
        String actuatorRouteId = "%s-actuator".formatted(serviceKey);

        routes.route(apiRouteId, routeCreator.createApiRoute(service));

        if (service.isApiDocEnabled()) {
            routes.route(apiDocRouteId, routeCreator.createApiDocRoute(service));
        }
        if (service.isActuatorEnabled()) {
            routes.route(actuatorRouteId, routeCreator.createActuatorRoute(service));
        }
    }

    private String getEurekaUrl () {
        return "http://%s:%s".formatted(eurekaProperties.host(), eurekaProperties.port());
    }
}

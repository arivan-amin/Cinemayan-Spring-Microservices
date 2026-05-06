package com.cinemayan.apigateway.application.routing;

import com.cinemayan.apigateway.domain.RouteCreator;
import com.cinemayan.apigateway.domain.ServiceRoute;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.builder.*;

import java.util.function.Function;

@RequiredArgsConstructor
@Slf4j
public class DefaultRouteCreator implements RouteCreator {

    private static final String LOAD_BALANCED_URI_TEMPLATE = "lb://%s-service";

    private static final String API_ROUTE_ID = "%s-service-api";
    private static final String API_ROUTE_PATH = "/%s/**";

    private static final String API_DOC_ROUTE_ID = "%s-service-api-doc";
    private static final String API_DOC_ROUTE_PATH = "/%s-service/api-docs";

    private static final String ACTUATOR_ROUTE_ID = "%s-service-actuator";
    private static final String ACTUATOR_ROUTE_PATH = "/actuator/%s/**";

    private final RedisRateLimiter rateLimiter;
    private final KeyResolver keyResolver;

    @Override
    public Function<PredicateSpec, Buildable<Route>> createApiRoute (ServiceRoute service) {
        log.info("creating API route for service = {}", service);
        String formattedPath = API_ROUTE_PATH.formatted(service.getPathPrefix());
        log.info("formattedPath = {}", formattedPath);
        return r -> r.path(formattedPath)
            .filters(filter -> applyRateLimiterIfEnabled(service, filter))
            .uri(createLoadBalancedUri(service));
    }

    private GatewayFilterSpec applyRateLimiterIfEnabled (ServiceRoute service,
                                                         GatewayFilterSpec filter) {
        if (service.isRateLimiterEnabled()) {
            return filter.requestRateLimiter(config -> config.setRateLimiter(rateLimiter)
                .setKeyResolver(keyResolver));
        }
        return filter;
    }

    private String createLoadBalancedUri (ServiceRoute service) {
        return LOAD_BALANCED_URI_TEMPLATE.formatted(service.getName());
    }

    @Override
    public Function<PredicateSpec, Buildable<Route>> createApiDocRoute (ServiceRoute service) {
        log.info("creating API doc route for service = {}", service);
        String formattedPath = API_DOC_ROUTE_PATH.formatted(service.getName());
        return r -> r.path(formattedPath)
            .filters(filter -> filter.setPath("/v3/api-docs"))
            .uri(createLoadBalancedUri(service));
    }

    @Override
    public Function<PredicateSpec, Buildable<Route>> createActuatorRoute (ServiceRoute service) {
        log.info("creating actuator route for service = {}", service);
        String formattedPath = ACTUATOR_ROUTE_PATH.formatted(service.getName());
        String serviceNameRegex = "/actuator/%s/(?<segment>.*)".formatted(service.getName());
        String redirectPath = "/actuator/${segment}";

        return r -> r.path(formattedPath)
            .filters(filter -> filter.rewritePath(serviceNameRegex, redirectPath))
            .uri(createLoadBalancedUri(service));
    }
}

package com.maya.cbs.api.gateway.application.routing;

import com.maya.cbs.api.gateway.domain.ServiceRoute;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;

import java.util.function.Function;

public interface RouteCreator {

    Function<PredicateSpec, Buildable<Route>> createApiRoute (ServiceRoute service);

    Function<PredicateSpec, Buildable<Route>> createApiDocRoute (ServiceRoute service);

    Function<PredicateSpec, Buildable<Route>> createActuatorRoute (ServiceRoute service);
}

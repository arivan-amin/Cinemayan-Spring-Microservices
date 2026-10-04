package com.maya.cbs.api.gateway.domain;

import lombok.Value;

@Value
public class ServiceRoute {

    String name;
    String pathPrefix;
    boolean actuatorEnabled;
    boolean apiDocEnabled;
    boolean rateLimiterEnabled;
}

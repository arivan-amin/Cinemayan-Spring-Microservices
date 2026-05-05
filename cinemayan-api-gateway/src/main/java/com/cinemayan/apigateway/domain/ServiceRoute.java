package com.cinemayan.apigateway.domain;

import lombok.Value;

@Value
public class ServiceRoute {

    String name;
    String pathPrefix;
    boolean actuatorEnabled;
    boolean apiDocEnabled;
    boolean rateLimiterEnabled;
}

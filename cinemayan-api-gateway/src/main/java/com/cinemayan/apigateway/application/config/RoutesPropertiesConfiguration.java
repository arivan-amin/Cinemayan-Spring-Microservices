package com.cinemayan.apigateway.application.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties (RouteProperties.class)
class RoutesPropertiesConfiguration {

}

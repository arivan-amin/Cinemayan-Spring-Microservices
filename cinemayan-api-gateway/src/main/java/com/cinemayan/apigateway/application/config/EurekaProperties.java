package com.cinemayan.apigateway.application.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties (prefix = "cinemayan.eureka")
public record EurekaProperties(String host, String port) {

}

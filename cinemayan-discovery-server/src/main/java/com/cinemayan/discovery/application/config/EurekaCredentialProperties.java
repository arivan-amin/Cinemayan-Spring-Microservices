package com.cinemayan.discovery.application.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties (prefix = "cinemayan.eureka.credentials")
public record EurekaCredentialProperties(String username, String password) {

}

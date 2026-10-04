package com.maya.cbs.discovery.application.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties (prefix = "maya.cbs.eureka.credentials")
public record EurekaCredentialProperties(
    String username,
    String password) {

}

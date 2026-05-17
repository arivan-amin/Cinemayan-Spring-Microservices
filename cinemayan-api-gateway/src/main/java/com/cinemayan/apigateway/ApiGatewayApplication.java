package com.cinemayan.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import static com.cinemayan.core.domain.config.CoreApplicationConfig.BASE_PACKAGE;

@SpringBootApplication (scanBasePackages = BASE_PACKAGE)
@ConfigurationPropertiesScan (basePackages = BASE_PACKAGE)
@EnableDiscoveryClient
public class ApiGatewayApplication {

    static void main (String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}

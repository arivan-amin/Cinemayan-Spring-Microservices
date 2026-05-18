package com.cinemayan.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import static com.cinemayan.core.domain.config.CoreApplicationConfig.BASE_PACKAGE;

@SpringBootApplication (scanBasePackages = BASE_PACKAGE)
@ConfigurationPropertiesScan (basePackages = BASE_PACKAGE)
@EnableEurekaServer
public class DiscoveryServerApplication {

    static void main (String[] args) {
        SpringApplication.run(DiscoveryServerApplication.class, args);
    }
}

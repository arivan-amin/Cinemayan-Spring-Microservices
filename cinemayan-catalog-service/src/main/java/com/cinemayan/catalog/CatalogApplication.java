package com.cinemayan.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import static com.cinemayan.core.domain.config.CoreApplicationConfig.BASE_PACKAGE;

@SpringBootApplication (scanBasePackages = BASE_PACKAGE)
@EnableJpaRepositories (basePackages = BASE_PACKAGE)
@EntityScan (basePackages = BASE_PACKAGE)
@ConfigurationPropertiesScan (basePackages = BASE_PACKAGE)
@EnableDiscoveryClient
@EnableCaching
@EnableScheduling
@EnableJpaAuditing
public class CatalogApplication {

    static void main (String[] args) {
        SpringApplication.run(CatalogApplication.class, args);
    }
}

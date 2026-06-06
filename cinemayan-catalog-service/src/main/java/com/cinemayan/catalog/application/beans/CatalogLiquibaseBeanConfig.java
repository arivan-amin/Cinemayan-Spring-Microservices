package com.cinemayan.catalog.application.beans;

import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import static com.cinemayan.core.domain.config.CoreApplicationConfig.LIQUIBASE_CHANGELOG_PATH;

@Configuration
@Slf4j
class CatalogLiquibaseBeanConfig {

    @Bean
    public SpringLiquibase liquibase (CatalogLiquibaseProperties properties) {
        log.info("Initializing Catalog Liquibase Bean");

        DataSource liquibaseDataSource = DataSourceBuilder.create()
            .url(properties.url())
            .username(properties.username())
            .password(properties.password())
            .build();

        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(liquibaseDataSource);
        liquibase.setChangeLog(LIQUIBASE_CHANGELOG_PATH);
        liquibase.setShouldRun(true);
        return liquibase;
    }
}

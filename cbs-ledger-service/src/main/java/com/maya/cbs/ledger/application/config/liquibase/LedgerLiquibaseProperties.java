package com.maya.cbs.ledger.application.config.liquibase;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties (prefix = "maya.cbs.ledger.liquibase")
public record LedgerLiquibaseProperties(
    @NotBlank
    String url,

    @NotBlank
    String username,

    @NotBlank
    String password) {

}

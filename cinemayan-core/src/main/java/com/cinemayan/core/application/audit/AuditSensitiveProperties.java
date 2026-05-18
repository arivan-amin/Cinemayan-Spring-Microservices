package com.cinemayan.core.application.audit;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@Validated
@ConfigurationProperties (prefix = "audit.masking.sensitive")
public record AuditSensitiveProperties(
    @NotEmpty
    Set<String> fields) {

}

package com.cinemayan.core.application.audit;

import jakarta.validation.constraints.NotEmpty;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@ConfigurationProperties (prefix = "audit.masking.sensitive")
@Validated
@Value
class AuditSensitiveProperties {

    @NotEmpty
    Set<String> fields;
}

package com.cinemayan.core.application.audit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
class CoreAuditLogBeansConfig {

    @Bean
    public AuditDataExtractor auditDataExtractor (
        @Value ("${spring.application.name}") String serviceName, Clock clock,
        SensitiveDataMasker dataMasker) {
        return new AuditDataExtractor(serviceName, clock, dataMasker);
    }
}

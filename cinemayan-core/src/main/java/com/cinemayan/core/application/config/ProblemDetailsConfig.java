package com.cinemayan.core.application.config;

import com.cinemayan.core.application.advice.ProblemDetailFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
class ProblemDetailsConfig {

    @Bean
    public ProblemDetailFactory problemDetailFactory (Clock clock) {
        return new ProblemDetailFactory(clock);
    }
}

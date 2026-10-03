package com.maya.cbs.core.application.config;

import com.maya.cbs.core.application.advice.ProblemDetailFactory;
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

package com.maya.cbs.core.application.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;
import java.util.TimeZone;

@Configuration
class LocaleConfiguration {

    @PostConstruct
    public void init () {
        Locale.setDefault(Locale.ENGLISH);
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}

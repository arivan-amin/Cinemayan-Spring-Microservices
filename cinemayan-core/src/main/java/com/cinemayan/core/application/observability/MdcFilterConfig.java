package com.cinemayan.core.application.observability;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
@RequiredArgsConstructor
class MdcFilterConfig {

    private final MdcPopulatingFilter mdcFilter;

    @Bean
    public FilterRegistrationBean<MdcPopulatingFilter> mdcFilterRegistrationBean () {
        FilterRegistrationBean<MdcPopulatingFilter> filterRegistration =
            new FilterRegistrationBean<>(mdcFilter);
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        filterRegistration.setName("mdcPopulatingFilter");
        return filterRegistration;
    }
}

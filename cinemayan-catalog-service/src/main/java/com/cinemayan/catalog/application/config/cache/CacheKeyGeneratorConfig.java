package com.cinemayan.catalog.application.config.cache;

import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
class CacheKeyGeneratorConfig implements CachingConfigurer {

    @Override
    public KeyGenerator keyGenerator () {
        return new DefaultCacheKeyGenerator();
    }
}

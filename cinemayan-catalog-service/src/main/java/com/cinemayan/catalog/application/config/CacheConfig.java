package com.cinemayan.catalog.application.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@EnableCaching
class CacheConfig {

    @Bean
    public CacheManager movieCacheManager () {
        CaffeineCacheManager manager =
            new CaffeineCacheManager(MovieCaches.GET_ALL_MOVIES, MovieCaches.GET_MOVIE_BY_ID,
                StudioCaches.GET_ALL_STUDIOS, StudioCaches.GET_STUDIO_BY_ID);
        manager.setCaffeine(Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofMinutes(10))
            .maximumSize(500));
        return manager;
    }
}

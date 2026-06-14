package com.cinemayan.catalog.application.config.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@EnableCaching
class CatalogCacheConfig {

    @Bean
    public CacheManager catalogCacheManager () {
        CaffeineCacheManager manager =
            new CaffeineCacheManager(MovieCaches.GET_ALL_MOVIES, MovieCaches.GET_MOVIE_BY_ID,
                StudioCaches.GET_ALL_STUDIOS, StudioCaches.GET_STUDIO_BY_ID);
        manager.setCaffeine(Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofMinutes(5))
            .maximumSize(500));
        return manager;
    }
}

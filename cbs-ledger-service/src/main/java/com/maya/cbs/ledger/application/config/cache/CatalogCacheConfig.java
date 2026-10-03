package com.maya.cbs.ledger.application.config.cache;

import com.maya.cbs.core.application.cache.CacheDefinition;
import com.maya.cbs.core.application.cache.CaffeineCacheHelper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
class CatalogCacheConfig {

    @Bean
    public CacheManager catalogCacheManager () {
        CaffeineCacheManager manager = new CaffeineCacheManager();
        for (CacheDefinition cache : CategoryCacheList.getCaches()) {
            manager.registerCustomCache(cache.getName(), CaffeineCacheHelper.toCaffeine(cache)
                .build());
        }
        return manager;
    }
}

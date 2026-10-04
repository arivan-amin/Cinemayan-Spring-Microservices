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
class LedgerCacheConfig {

    @Bean
    public CacheManager ledgerCacheManager () {
        CaffeineCacheManager manager = new CaffeineCacheManager();
        for (CacheDefinition cache : LedgerCacheList.getCaches()) {
            manager.registerCustomCache(cache.getName(), CaffeineCacheHelper.toCaffeine(cache)
                .build());
        }
        return manager;
    }
}

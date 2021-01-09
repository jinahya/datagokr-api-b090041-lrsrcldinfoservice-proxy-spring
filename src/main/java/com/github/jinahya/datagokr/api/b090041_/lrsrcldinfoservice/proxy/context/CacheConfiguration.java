package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;

@EnableCaching
@Configuration
@Slf4j
public class CacheConfiguration {

//    @Bean
    public CacheManagerCustomizer<JCacheCacheManager> cacheManagerCustomizer() {
        return jcacheCacheManager -> {
            log.debug("cache cache manager: {}", jcacheCacheManager);
            final CacheManager cacheManager = jcacheCacheManager.getCacheManager();
            log.debug("cache manager: {}", cacheManager);
        };
    }
}

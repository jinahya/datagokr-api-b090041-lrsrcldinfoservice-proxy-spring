package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

@Slf4j
class CacheErrorHandler_ implements CacheErrorHandler {

    @Override
    public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {

    }

    @Override
    public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {

    }

    @Override
    public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {

    }

    @Override
    public void handleCacheClearError(RuntimeException exception, Cache cache) {

    }
}

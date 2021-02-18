package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

@Slf4j
class LoggingCacheErrorHandler implements CacheErrorHandler {

    @Override
    public void handleCacheGetError(final RuntimeException exception, final Cache cache, final Object key) {
        log.error("failed to get cache; cache: {}, key: {}", cache, key, exception);
    }

    @Override
    public void handleCachePutError(final RuntimeException exception, final Cache cache, final Object key,
                                    final Object value) {
        log.error("failed to put cache; cache: {}, key: {}, value: {}", cache, key, value, exception);
    }

    @Override
    public void handleCacheEvictError(final RuntimeException exception, final Cache cache, final Object key) {
        log.error("failed to evict cache; cache: {}, key: {}", cache, key, exception);
    }

    @Override
    public void handleCacheClearError(final RuntimeException exception, final Cache cache) {
        log.error("failed to clear cache; cache: {}", cache, exception);
    }
}

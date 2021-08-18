package com.skrivet.plugins.security.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CacheManager implements org.apache.shiro.cache.CacheManager {
    private org.springframework.cache.CacheManager cacheManager;
    private final ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<>(16);

    public CacheManager(org.springframework.cache.CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        Cache cache = this.cacheMap.get(name);
        if (cache != null) {
            return cache;
        }
        synchronized (this.cacheMap) {
            cache = new com.skrivet.plugins.security.shiro.cache.Cache(cacheManager.getCache(name));
            this.cacheMap.put(name, cache);
        }
        return cache;
    }
}
package com.skrivet.plugins.cache.core.provider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractCacheProvider implements CacheProvider {
    private Map<String, Cache> caches = new ConcurrentHashMap<>();

    @Override
    public Cache getCache(String name) {
        Cache cache = this.caches.get(name);
        if (cache == null) {
            synchronized (this.caches) {
                cache = load(name);
                caches.put(name, cache);
            }
        }
        return caches.get(name);
    }

    public abstract Cache load(String name);

}

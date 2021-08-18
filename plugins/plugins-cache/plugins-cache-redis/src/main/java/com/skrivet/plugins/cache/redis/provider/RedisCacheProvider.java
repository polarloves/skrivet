package com.skrivet.plugins.cache.redis.provider;

import com.skrivet.plugins.cache.core.provider.AbstractCacheProvider;
import com.skrivet.plugins.cache.core.provider.Cache;
import com.skrivet.plugins.cache.redis.RedisCache;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisCacheProvider extends AbstractCacheProvider {
    private String projectName;
    private RedisTemplate redisTemplate;

    public RedisCacheProvider(String projectName, RedisTemplate redisTemplate) {
        this.projectName = projectName;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Cache load(String name) {
        RedisCache cache = new RedisCache(redisTemplate);
        cache.setCacheName(projectName + "::providerCache::" + name);
        return cache;
    }
}

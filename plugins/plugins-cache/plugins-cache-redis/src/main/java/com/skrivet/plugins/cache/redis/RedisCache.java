package com.skrivet.plugins.cache.redis;

import com.skrivet.plugins.cache.core.provider.AbstractCache;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisCache extends AbstractCache {
    private RedisTemplate redisTemplate;

    public RedisCache(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public <T> T get(Object key) {
        return (T) redisTemplate.opsForHash().get(name, key);
    }

    @Override
    public void set(Object key, Object value) {
        redisTemplate.opsForHash().put(name, key, value);
    }

    @Override
    public void delete(Object key) {
        redisTemplate.opsForHash().delete(name, key);
    }

    @Override
    public void clear() {
        redisTemplate.delete(name);
    }
}

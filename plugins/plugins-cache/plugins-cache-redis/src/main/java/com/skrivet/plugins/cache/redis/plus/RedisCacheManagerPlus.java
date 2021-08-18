package com.skrivet.plugins.cache.redis.plus;

import org.springframework.cache.transaction.AbstractTransactionSupportingCacheManager;
import org.springframework.data.redis.cache.*;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.*;

public class RedisCacheManagerPlus extends AbstractTransactionSupportingCacheManager {

    private final RedisCacheWriter cacheWriter;
    private final RedisCacheConfiguration defaultCacheConfig;
    private final Map<String, RedisCacheConfiguration> initialCacheConfiguration;
    private final boolean allowInFlightCacheCreation;


    public RedisCacheManagerPlus(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration,
                                 boolean allowInFlightCacheCreation) {
        Assert.notNull(cacheWriter, "CacheWriter must not be null!");
        Assert.notNull(defaultCacheConfiguration, "DefaultCacheConfiguration must not be null!");
        this.cacheWriter = cacheWriter;
        this.defaultCacheConfig = defaultCacheConfiguration;
        this.initialCacheConfiguration = new LinkedHashMap<>();
        this.allowInFlightCacheCreation = allowInFlightCacheCreation;
    }


    public RedisCacheManagerPlus(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        this(cacheWriter, defaultCacheConfiguration, true);
    }


    public RedisCacheManagerPlus(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration,
                                 String... initialCacheNames) {

        this(cacheWriter, defaultCacheConfiguration, true, initialCacheNames);
    }


    public RedisCacheManagerPlus(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration,
                                 boolean allowInFlightCacheCreation, String... initialCacheNames) {

        this(cacheWriter, defaultCacheConfiguration, allowInFlightCacheCreation);

        for (String cacheName : initialCacheNames) {
            this.initialCacheConfiguration.put(cacheName, defaultCacheConfiguration);
        }
    }


    public RedisCacheManagerPlus(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration,
                                 Map<String, RedisCacheConfiguration> initialCacheConfigurations) {

        this(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations, true);
    }


    public RedisCacheManagerPlus(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration,
                                 Map<String, RedisCacheConfiguration> initialCacheConfigurations, boolean allowInFlightCacheCreation) {

        this(cacheWriter, defaultCacheConfiguration, allowInFlightCacheCreation);

        Assert.notNull(initialCacheConfigurations, "InitialCacheConfigurations must not be null!");

        this.initialCacheConfiguration.putAll(initialCacheConfigurations);
    }


    @Override
    protected Collection<RedisCache> loadCaches() {

        List<RedisCache> caches = new LinkedList<>();

        for (Map.Entry<String, RedisCacheConfiguration> entry : initialCacheConfiguration.entrySet()) {
            caches.add(createRedisCache(entry.getKey(), entry.getValue()));
        }

        return caches;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.cache.support.AbstractCacheManager#getMissingCache(java.lang.String)
     */
    @Override
    protected RedisCache getMissingCache(String name) {
        return allowInFlightCacheCreation ? createRedisCache(name, defaultCacheConfig) : null;
    }

    /**
     * @return unmodifiable {@link Map} containing cache name / configuration pairs. Never {@literal null}.
     */
    public Map<String, RedisCacheConfiguration> getCacheConfigurations() {

        Map<String, RedisCacheConfiguration> configurationMap = new HashMap<>(getCacheNames().size());

        getCacheNames().forEach(it -> {

            RedisCache cache = RedisCache.class.cast(lookupCache(it));
            configurationMap.put(it, cache != null ? cache.getCacheConfiguration() : null);
        });

        return Collections.unmodifiableMap(configurationMap);
    }


    protected RedisCache createRedisCache(String name, @Nullable RedisCacheConfiguration cacheConfig) {
        return new RedisCachePlus(name, cacheWriter, cacheConfig != null ? cacheConfig : defaultCacheConfig);
    }


}

package com.skrivet.plugins.cache.core;

import com.skrivet.core.common.serializer.SerializerType;

import java.util.Map;

public interface CacheSerializerInitialConfigurations {
    public Map<String, CacheConfig> initialCacheSerializerConfigurations();
}

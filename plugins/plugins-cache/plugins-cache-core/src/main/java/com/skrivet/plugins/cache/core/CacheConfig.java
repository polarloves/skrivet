package com.skrivet.plugins.cache.core;

import com.skrivet.core.common.serializer.SerializerType;

import java.time.Duration;
public class CacheConfig {
    private SerializerType serializerType;
    private Duration duration;

    public SerializerType getSerializerType() {
        return serializerType;
    }

    public void setSerializerType(SerializerType serializerType) {
        this.serializerType = serializerType;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public CacheConfig(SerializerType serializerType, Duration duration) {
        this.serializerType = serializerType;
        this.duration = duration;
    }
}

package com.skrivet.components.redis.properties;

import com.skrivet.core.common.serializer.SerializerType;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "skrivet.redis")
public class RedisProperties extends org.springframework.boot.autoconfigure.data.redis.RedisProperties {
    private SerializerType keySerialization = SerializerType.STRING;
    private SerializerType valueSerialization = SerializerType.JSON;
    private SerializerType hashKeySerialization = SerializerType.STRING;
    private SerializerType hashValueSerialization = SerializerType.JSON;

    public SerializerType getKeySerialization() {
        return keySerialization;
    }

    public void setKeySerialization(SerializerType keySerialization) {
        this.keySerialization = keySerialization;
    }

    public SerializerType getValueSerialization() {
        return valueSerialization;
    }

    public void setValueSerialization(SerializerType valueSerialization) {
        this.valueSerialization = valueSerialization;
    }

    public SerializerType getHashKeySerialization() {
        return hashKeySerialization;
    }

    public void setHashKeySerialization(SerializerType hashKeySerialization) {
        this.hashKeySerialization = hashKeySerialization;
    }

    public SerializerType getHashValueSerialization() {
        return hashValueSerialization;
    }

    public void setHashValueSerialization(SerializerType hashValueSerialization) {
        this.hashValueSerialization = hashValueSerialization;
    }
}

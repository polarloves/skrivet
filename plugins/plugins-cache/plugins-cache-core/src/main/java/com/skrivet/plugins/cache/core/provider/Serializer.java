package com.skrivet.plugins.cache.core.provider;

public interface Serializer {
    /**
     * 序列化
     *
     * @param data
     * @return
     */
    public byte[] serialize(Object data);

    /**
     * 反序列化
     *
     * @param data
     * @return
     */
    public Object deserialize(byte[] data);
}


package com.skrivet.core.common.serializer;


public interface Serializer {
    public byte[] serialize(Object obj);

    public Object deserialize(byte[] bytes);
}

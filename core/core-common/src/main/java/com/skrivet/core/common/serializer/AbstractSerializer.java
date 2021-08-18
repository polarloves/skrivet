package com.skrivet.core.common.serializer;

import com.skrivet.core.common.exception.SerializationExp;

public abstract class AbstractSerializer implements Serializer {
    public abstract byte[] doSerialize(Object obj) throws Exception;

    public abstract Object doDeserialize(byte[] bytes) throws Exception;

    @Override
    public byte[] serialize(Object obj) {
        try {
            if (obj == null) {
                return new byte[0];
            }
            return doSerialize(obj);
        } catch (Exception e) {
            throw new SerializationExp().copyStackTrace(e);
        }
    }

    @Override
    public Object deserialize(byte[] bytes) {
        try {
            if (bytes == null) {
                return null;
            }
            return doDeserialize(bytes);
        } catch (Exception e) {
            throw new SerializationExp().copyStackTrace(e);
        }
    }
}

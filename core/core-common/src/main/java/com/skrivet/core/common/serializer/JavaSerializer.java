package com.skrivet.core.common.serializer;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;

public class JavaSerializer extends AbstractSerializer {
    private Converter<Object, byte[]> serializer;
    private Converter<byte[], Object> deserializer;

    public JavaSerializer() {
        this.serializer = new SerializingConverter();
        this.deserializer = new DeserializingConverter();
    }

    @Override
    public byte[] doSerialize(Object obj) throws Exception {
        return serializer.convert(obj);
    }

    @Override
    public Object doDeserialize(byte[] bytes) throws Exception {
        return deserializer.convert(bytes);
    }

}

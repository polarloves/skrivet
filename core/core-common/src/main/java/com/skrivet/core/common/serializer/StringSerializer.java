package com.skrivet.core.common.serializer;

import com.skrivet.core.common.exception.NotSupportExp;

public class StringSerializer extends AbstractSerializer {
    @Override
    public byte[] doSerialize(Object obj) throws Exception {
        if (!(obj instanceof String)) {
            throw new NotSupportExp();
        }
        return ((String) obj).getBytes("UTF-8");
    }

    @Override
    public Object doDeserialize(byte[] bytes) throws Exception {
        return new String(bytes, "UTF-8");
    }

}

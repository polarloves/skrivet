package com.skrivet.core.common.exception;

import com.skrivet.core.common.enums.Code;

/**
 * 序列号异常
 */
public class SerializationExp extends BuildableExp {

    public SerializationExp() {
        this(null);
    }

    public SerializationExp(String tip) {
        super(Code.SERVER_SERIALIZATION.getCode(), tip);
    }
}

package com.skrivet.core.common.serializer;

public enum SerializerType {
    STRING("string"), JSON("json"), JAVA("java");
    String code;

    SerializerType(String code) {
        this.code = code;
    }
}

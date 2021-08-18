package com.skrivet.core.common.entity;

import org.springframework.lang.Nullable;

import java.io.Serializable;

public class ValueWrapper implements Serializable {
    @Nullable
    private final Serializable value;

    public ValueWrapper(@Nullable Serializable value) {
        this.value = value;
    }


    public Serializable get() {
        return this.value;
    }
}

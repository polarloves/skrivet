package com.skrivet.core.common.convert.impl;

import com.skrivet.core.common.convert.IdGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import java.util.UUID;

public class UUIDGenerator implements IdGenerator {
    @Override
    public String generate() {
        return UUID.randomUUID().toString().replace("-","");
    }
}

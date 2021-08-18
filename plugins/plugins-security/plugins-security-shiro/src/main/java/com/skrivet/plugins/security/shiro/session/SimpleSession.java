package com.skrivet.plugins.security.shiro.session;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skrivet.plugins.cache.core.CacheTimeoutChangeable;
import org.apache.shiro.session.InvalidSessionException;

import java.util.Collection;

public class SimpleSession extends org.apache.shiro.session.mgt.SimpleSession implements CacheTimeoutChangeable {

    public SimpleSession() {
    }

    public SimpleSession(String host) {
        super(host);
    }

    @Override
    public boolean isValid() {
        return super.isValid();
    }

    @Override
    public Collection<Object> getAttributeKeys() throws InvalidSessionException {
        return super.getAttributeKeys();
    }
}

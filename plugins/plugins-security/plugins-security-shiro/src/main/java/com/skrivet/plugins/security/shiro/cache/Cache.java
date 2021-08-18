package com.skrivet.plugins.security.shiro.cache;

import com.skrivet.core.common.exception.NotSupportExp;
import org.apache.shiro.cache.CacheException;

import java.util.Collection;
import java.util.Set;

public class Cache implements org.apache.shiro.cache.Cache {
    private org.springframework.cache.Cache cache;

    public Cache(org.springframework.cache.Cache cache) {
        this.cache = cache;
    }

    @Override
    public Object get(Object o) throws CacheException {
        org.springframework.cache.Cache.ValueWrapper valueWrapper = cache.get(o);
        return valueWrapper == null ? null : valueWrapper.get();
    }

    @Override
    public Object put(Object o, Object o2) throws CacheException {
        cache.put(o, o2);
        return o2;
    }

    @Override
    public Object remove(Object o) throws CacheException {
        org.springframework.cache.Cache.ValueWrapper valueWrapper = cache.get(o);
        if (valueWrapper != null) {
            cache.evict(o);
            return valueWrapper.get();
        }
        return null;
    }

    @Override
    public void clear() throws CacheException {
        cache.clear();
    }

    @Override
    public int size() {
        throw new NotSupportExp().variable("shiro cache size");
    }

    @Override
    public Set keys() {
        throw new NotSupportExp().variable("shiro cache keys");
    }

    @Override
    public Collection values() {
        throw new NotSupportExp().variable("shiro cache values");

    }
}

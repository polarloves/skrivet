package com.skrivet.plugins.cache.core.provider;

/**
 * 自定义的缓存，和spring的缓存不一样
 */
public interface Cache {

    public <T> T get(Object key);

    public void set(Object key,Object value);

    public void delete(Object key);

    public void clear();

    public void setCacheName(String name);

    public String cacheName();

}

package com.skrivet.plugins.cache.core;

/**
 * 当value实现此接口时，当执行缓存更新的操作时，会同步修改缓存过期时间
 */
public interface CacheTimeoutChangeable {
    public long getTimeout();
}

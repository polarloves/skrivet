package com.skrivet.plugins.cache.core.provider;

public abstract class AbstractCache implements Cache {
    protected String name;

    public void setCacheName(String name) {
        this.name = name;
    }

    public String cacheName() {
        return name;
    }

}

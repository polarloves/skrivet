package com.skrivet.components.shiro.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "skrivet.shiro")
//@Component
public class ShiroProperties {
    private CookieProperties cookie = new CookieProperties();
    private SessionProperties session = new SessionProperties();
    private CacheProperties cache = new CacheProperties();
    private AccountProperties accountProperties = new AccountProperties();
    private String loginUrl = "/user/session/login";

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public AccountProperties getAccountProperties() {
        return accountProperties;
    }

    public void setAccountProperties(AccountProperties accountProperties) {
        this.accountProperties = accountProperties;
    }

    public CacheProperties getCache() {
        return cache;
    }

    public void setCache(CacheProperties cache) {
        this.cache = cache;
    }

    public CookieProperties getCookie() {
        return cookie;
    }

    public void setCookie(CookieProperties cookie) {
        this.cookie = cookie;
    }

    public SessionProperties getSession() {
        return session;
    }

    public void setSession(SessionProperties session) {
        this.session = session;
    }
}

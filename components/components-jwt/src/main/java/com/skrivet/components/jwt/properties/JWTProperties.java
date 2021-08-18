package com.skrivet.components.jwt.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * JWT配置文件
 */
@ConfigurationProperties(prefix = "skrivet.jwt")
public class JWTProperties {
    //头文件中存储token的key
    private String tokenKey = "token";
    //头文件中存储action的key
    private String actionKey = "action";
    //签名秘钥
    private String signKey;
    //token超时时间
    private long timeout = 30 * 60 * 1000;
    //token刷新时间，当token离超时不到refreshBeforeTimeout时，将会在action中存入刷新动作，交由客户端进行刷新
    private long refreshBeforeTimeout = 5 * 60 * 1000;

    public String getTokenKey() {
        return tokenKey;
    }

    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
    }

    public String getActionKey() {
        return actionKey;
    }

    public void setActionKey(String actionKey) {
        this.actionKey = actionKey;
    }

    public String getSignKey() {
        return signKey;
    }

    public void setSignKey(String signKey) {
        this.signKey = signKey;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public long getRefreshBeforeTimeout() {
        return refreshBeforeTimeout;
    }

    public void setRefreshBeforeTimeout(long refreshBeforeTimeout) {
        this.refreshBeforeTimeout = refreshBeforeTimeout;
    }
}

package com.skrivet.components.shiro.properties;

public class CacheProperties {
    // 用户登录凭证缓存key
    private String tokenCacheName = "shiroTokenCache";
    //用户权限信息缓存key
    private String permissionInfoCacheName = "shiroPermissionCache";
    //重试密码缓存key
    private String retryCacheName = "shiroRetryCache";
    //在线用户缓存key
    private String onlineUserCacheName = "shiroOnlineUser";

    public String getRetryCacheName() {
        return retryCacheName;
    }

    public void setRetryCacheName(String retryCacheName) {
        this.retryCacheName = retryCacheName;
    }

    public String getOnlineUserCacheName() {
        return onlineUserCacheName;
    }

    public void setOnlineUserCacheName(String onlineUserCacheName) {
        this.onlineUserCacheName = onlineUserCacheName;
    }

    public String getTokenCacheName() {
        return tokenCacheName;
    }

    public void setTokenCacheName(String tokenCacheName) {
        this.tokenCacheName = tokenCacheName;
    }

    public String getPermissionInfoCacheName() {
        return permissionInfoCacheName;
    }

    public void setPermissionInfoCacheName(String permissionInfoCacheName) {
        this.permissionInfoCacheName = permissionInfoCacheName;
    }
}

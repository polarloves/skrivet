package com.skrivet.plugins.security.shiro.filter;

import com.skrivet.plugins.security.core.service.SecurityService;
import com.skrivet.plugins.security.shiro.principal.ShiroPrincipal;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.AdviceFilter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 并发人数控制，拦截其登录请求
 */
public class ConcurrentControlFilter extends AdviceFilter {
    // 活动的用户的缓存
    private Cache<String, List<Serializable>> onlineUserCache;
    private Cache sessionCache;
    private int maxSession = 1;
    private SecurityService securityService;
    private CacheManager cacheManager;
    private String onlineUserCacheName;
    private String sessionCacheName;
    @Override
    protected void postHandle(ServletRequest request, ServletResponse response) throws Exception {
        if (onlineUserCache == null) {
            onlineUserCache = cacheManager.getCache(onlineUserCacheName);
        }
        if (sessionCache == null) {
            sessionCache = cacheManager.getCache(sessionCacheName);
        }
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            // 如果没有登录，直接进行之后的流程
            return;
        }
        ShiroPrincipal principal = (ShiroPrincipal) subject.getPrincipal();
        String userId = principal.getUser().getId();
        boolean flag = false;
        List<Serializable> caches = onlineUserCache.get(userId);
        List<Serializable> removeList = new ArrayList<>();
        if (caches != null && caches.size() > 0) {
            for (Serializable sessionId : caches) {
                if (sessionCache.get(sessionId) == null) {
                    //会话不存在，移除掉...
                    removeList.add(sessionId);
                }
            }
            if(removeList != null && removeList.size() > 0){
                caches.removeAll(removeList);
                flag = true;
            }
        }

        if (maxSession > 0 && caches != null && caches.size() > 0) {
            while (caches.size() > maxSession) {
                flag = true;
                Serializable sessionId = caches.remove(0);
                securityService.shotOffBySessionId((String) sessionId, null);
            }
        }
        if (flag) {
            onlineUserCache.put(userId, caches);
        }
    }

    public void setSessionCacheName(String sessionCacheName) {
        this.sessionCacheName = sessionCacheName;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void setOnlineUserCacheName(String onlineUserCacheName) {
        this.onlineUserCacheName = onlineUserCacheName;
    }
}

package com.skrivet.plugins.security.shiro.session;

import com.skrivet.plugins.security.shiro.principal.ShiroPrincipal;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Deque;
import java.util.List;

/**
 * 使用session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)来获取用户凭证。
 * 详见org.apache.shiro.mgt.DefaultSubjectDAO
 *
 * @author N
 */
public class SessionListener implements org.apache.shiro.session.SessionListener {
    // 活动的用户的缓存
    private Cache<String, List<Serializable>> onlineUserCache;
    private CacheManager cacheManager;
    private String onlineUserCacheName;

    public SessionListener(CacheManager cacheManager, String onlineUserCacheName) {
        this.cacheManager = cacheManager;
        this.onlineUserCacheName = onlineUserCacheName;
        initBean();
    }

    public void initBean() {
        this.onlineUserCache = cacheManager.getCache(onlineUserCacheName);
    }

    @Override
    public void onStart(Session session) {// 会话创建触发 已进入shiro的过滤连就触发这个方法

    }

    @Override
    public void onStop(Session session) {// 退出

    }

    @Override
    public void onExpiration(Session session) {// 会话过期时触发
        PrincipalCollection principalCollection = (PrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        if (principalCollection != null) {
            ShiroPrincipal principal = (ShiroPrincipal) principalCollection.getPrimaryPrincipal();
            if (principal != null) {
                List<Serializable> sessions = onlineUserCache.get(principal.getUser().getId() + "");
                if (sessions != null && !sessions.isEmpty()) {
                    sessions.remove(session.getId());
                    onlineUserCache.put(principal.getUser().getId(), sessions);
                }
            }
        }
    }

}

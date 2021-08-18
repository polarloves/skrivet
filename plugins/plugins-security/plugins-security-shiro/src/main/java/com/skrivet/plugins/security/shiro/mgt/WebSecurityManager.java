package com.skrivet.plugins.security.shiro.mgt;

import com.skrivet.plugins.security.shiro.principal.ShiroPrincipal;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

import java.io.Serializable;
import java.util.List;

public class WebSecurityManager extends DefaultWebSecurityManager {
    private Cache<String, List<Serializable>> onlineUserCache;
    private CacheManager cacheManager;
    private String onlineUserCacheName;

    public WebSecurityManager(CacheManager cacheManager, String onlineUserCacheName) {
        this.cacheManager = cacheManager;
        this.onlineUserCacheName = onlineUserCacheName;
        initBean();
    }

    public void initBean() {
        this.onlineUserCache = cacheManager.getCache(onlineUserCacheName);
    }

    public AuthenticationInfo authenticate(AuthenticationToken token) throws AuthenticationException {
        return super.authenticate(token);
    }

    @Override
    protected void beforeLogout(Subject subject) {
        Session session = subject.getSession(false);
        if (session != null) {
            PrincipalCollection principalCollection = (PrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if (principalCollection != null) {
                ShiroPrincipal principal = (ShiroPrincipal) principalCollection.getPrimaryPrincipal();
                if (principal != null) {
                    List<Serializable> sessions = onlineUserCache.get(principal.getUser().getId());
                    if (sessions != null && !sessions.isEmpty()) {
                        sessions.remove(session.getId());
                        onlineUserCache.put(principal.getUser().getId(), sessions);
                    }
                }
            }
        }
        super.beforeLogout(subject);
    }
}

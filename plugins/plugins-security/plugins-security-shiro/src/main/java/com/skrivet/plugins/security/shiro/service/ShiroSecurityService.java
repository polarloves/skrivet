package com.skrivet.plugins.security.shiro.service;

import com.skrivet.components.shiro.token.UserNamePasswordToken;
import com.skrivet.core.common.exception.account.IncorrectPasswordExp;
import com.skrivet.core.common.exception.account.ShotOffExp;
import com.skrivet.core.toolkit.DateUtils;
import com.skrivet.plugins.security.core.entity.ActiveUser;
import com.skrivet.plugins.security.core.entity.PermissionSet;
import com.skrivet.plugins.security.core.entity.User;
import com.skrivet.plugins.security.core.service.SecurityService;
import com.skrivet.plugins.security.shiro.cache.CacheManager;
import com.skrivet.plugins.security.shiro.principal.ShiroPrincipal;
import com.skrivet.plugins.security.shiro.realm.PermissionSetAware;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static com.skrivet.plugins.security.core.holder.FilterHolder.EXCEPTION_KEY;


public class ShiroSecurityService implements SecurityService {
    private String tokenCacheName;
    private String permissionInfoCacheName;
    private CacheManager cacheManager;
    private SessionDAO sessionDAO;
    private String onlineUserCacheName;
    private SessionManager sessionManager;

    public void setSessionDAO(SessionDAO sessionDAO) {
        this.sessionDAO = sessionDAO;
    }

    public void setOnlineUserCacheName(String onlineUserCacheName) {
        this.onlineUserCacheName = onlineUserCacheName;
    }

    public void setTokenCacheName(String tokenCacheName) {
        this.tokenCacheName = tokenCacheName;
    }

    public void setPermissionInfoCacheName(String permissionInfoCacheName) {
        this.permissionInfoCacheName = permissionInfoCacheName;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public List<ActiveUser> activeUser(String userId) {
        List<ActiveUser> activeUsers = new ArrayList<>();
        Cache<String, List<Serializable>> onlineUserCache = cacheManager.getCache(onlineUserCacheName);
        List<Serializable> caches = onlineUserCache.get(userId);
        if (caches != null) {
            for (Serializable sessionId : caches) {
                Session session = sessionDAO.readSession(sessionId);
                if (session != null) {
                    if (session.getAttribute(EXCEPTION_KEY) != null) {
                        continue;
                    }
                    ActiveUser activeUser = new ActiveUser();
                    activeUser.setId((String) sessionId);
                    activeUser.setCreateTime(DateUtils.formatDateTime(session.getStartTimestamp()));
                    activeUser.setLastAccessTime(DateUtils.formatDateTime(session.getLastAccessTime()));
                    activeUser.setHost(session.getHost());
                    if (session.getTimeout() < 0) {
                        //永不过期
                        activeUser.setExpireTime("-");
                    } else {
                        if (session.getTimeout() + session.getLastAccessTime().getTime() < new Date().getTime()) {
                            //此session已过期
                            continue;
                        }
                        activeUser.setExpireTime(DateUtils.formatDateTime(new Date(session.getTimeout() + session.getLastAccessTime().getTime())));
                    }
                    activeUsers.add(activeUser);
                }
            }
        }
        return activeUsers;
    }

    @Override
    public String currentUserId() {
        if (SecurityUtils.getSubject().getPrincipal() == null) {
            return null;
        }
        ShiroPrincipal shiroPrincipal = (ShiroPrincipal) SecurityUtils.getSubject().getPrincipal();
        return shiroPrincipal.getUser().getId();
    }

    @Override
    public User currentUser() {
        if (SecurityUtils.getSubject().getPrincipal() == null) {
            return null;
        }
        ShiroPrincipal shiroPrincipal = (ShiroPrincipal) SecurityUtils.getSubject().getPrincipal();
        User user = shiroPrincipal.getUser();
        if (user != null) {
            DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
            Collection<Realm> realms = securityManager.getRealms();
            for (Realm realm : realms) {
                if (realm instanceof PermissionSetAware) {
                    PermissionSetAware permissionSetAware = (PermissionSetAware) realm;
                    PermissionSet permissionSet = permissionSetAware.getPermissionSet(user.getId());
                    user.setRoles(permissionSet.getRoles());
                    user.setPermissions(permissionSet.getPermissions());
                    break;
                }
            }
        }
        return user;
    }

    @Override
    public void shotOffBySessionId(String sessionId, String msg) {
        Session session = sessionDAO.readSession(sessionId);
        if (session != null) {
            session.setAttribute(EXCEPTION_KEY, new ShotOffExp(msg));
            sessionDAO.update(session);
        }
    }

    @Override
    public void shotOffByUserId(String userId, String msg) {
        Cache<String, List<Serializable>> onlineUserCache = cacheManager.getCache(onlineUserCacheName);
        List<Serializable> caches = onlineUserCache.get(userId);
        if (caches != null) {
            for (Serializable sessionId : caches) {
                shotOffBySessionId((String) sessionId, msg);
            }
            caches.clear();
            onlineUserCache.put(userId, caches);
        }
    }

    @Override
    public void removeTokenCache(String userName) {
        if (StringUtils.isEmpty(userName)) {
            cacheManager.getCache(tokenCacheName).clear();
        } else {
            cacheManager.getCache(tokenCacheName).remove(userName);
        }
    }

    @Override
    public void removePermissionCache(String userId) {
        if (StringUtils.isEmpty(userId)) {
            cacheManager.getCache(permissionInfoCacheName).clear();
        } else {
            cacheManager.getCache(permissionInfoCacheName).remove(userId);
        }
    }

    @Override
    public String login(String userName, String password, boolean checkPassword, boolean alwaysActive) {
        UserNamePasswordToken token = new UserNamePasswordToken();
        token.setUsername(userName);
        token.setPassword(password.toCharArray());
        token.setValidate(checkPassword);
        try {
            SecurityUtils.getSubject().login(token);
            if (alwaysActive) {
                SecurityUtils.getSubject().getSession().setTimeout(-1000l);
            }
            return (String) SecurityUtils.getSubject().getSession().getId();
        } catch (IncorrectCredentialsException e) {
            throw new IncorrectPasswordExp();
        }
    }

    @Override
    public void logout() {
        SecurityUtils.getSubject().logout();
    }

}

package com.skrivet.plugins.security.shiro.matcher;

import com.skrivet.components.shiro.token.UserNamePasswordToken;
import com.skrivet.core.common.enums.Code;
import com.skrivet.core.common.exception.BuildableExp;
import com.skrivet.plugins.security.shiro.principal.ShiroPrincipal;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.crypto.UnknownAlgorithmException;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.skrivet.plugins.security.core.holder.FilterHolder.EXCEPTION_KEY;

/**
 * 凭证匹配器，如果密码输入次数大于设置的值，则抛出异常。
 *
 * @author PolarLoves
 */

public class ShiroLimitRetryCountMatcher extends HashedCredentialsMatcher {
    private Logger logger = LoggerFactory.getLogger(getClass());

    // AtomicInteger 线程安全的int类，passwordRetryCache为重新输入次数
    private Cache<String, FaultMessage> retryCache;
    private long lockTime = 60 * 1000 * 30l;
    //活动的用户的缓存
    private Cache<String, List<Serializable>> onLineUserCache;

    private CacheManager cacheManager;
    private String retryCacheName;
    private String onlineUserCacheName;
    private int retryCount;


    private static class FaultMessage implements Serializable {
        private static final long serialVersionUID = 1726833032596862634L;
        private AtomicInteger num;
        private long lastTime;
    }

    public Cache<String, FaultMessage> getRetryCache() {
        if (retryCache == null) {
            retryCache = cacheManager.getCache(retryCacheName);
        }
        return retryCache;
    }

    public Cache<String, List<Serializable>> getOnLineUserCache() {
        if (onLineUserCache == null) {
            onLineUserCache = cacheManager.getCache(onlineUserCacheName);
        }
        return onLineUserCache;
    }


    protected Hash hashProvidedCredentials(Object credentials, Object salt, int hashIterations) {
        String hashAlgorithmName = getHashAlgorithmName();
        return new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations) {
            @Override
            protected byte[] hash(byte[] bytes, byte[] salt, int hashIterations) throws UnknownAlgorithmException {
                try {
                    return com.skrivet.plugins.security.core.encryption.Hash.hash(hashAlgorithmName, bytes, salt, hashIterations);
                } catch (NoSuchAlgorithmException e) {
                    throw new UnknownAlgorithmException("error in hash");
                }
            }
        };
    }


    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UserNamePasswordToken realToken = (UserNamePasswordToken) token;
        String username = (String) token.getPrincipal();
        boolean matches = false;
        if (realToken.isValidate()) {
            matches = super.doCredentialsMatch(token, info);
        } else {
            //不校验密码，则返回true
            matches = true;
        }
        if (retryCount > 0) {
            boolean faultMessageExist = false;
            FaultMessage faultMessage = getRetryCache().get(username);
            if (faultMessage == null) {
                faultMessage = new FaultMessage();
                faultMessage.num = new AtomicInteger(0);
                faultMessage.lastTime = new Date().getTime();
                faultMessageExist = false;
            } else {
                if (faultMessage.lastTime + lockTime < new Date().getTime()) {
                    // 已经过期啦~
                    faultMessage = new FaultMessage();
                    faultMessage.num = new AtomicInteger(0);
                    faultMessage.lastTime = new Date().getTime();
                    getRetryCache().remove(username);
                } else {
                    faultMessageExist = true;
                }
            }
            if (faultMessage.num.intValue() >= retryCount) {
                //账号被锁定
                long time = (faultMessage.lastTime + lockTime - new Date().getTime()) / 1000 / 60;
                throw new AuthenticationException(new BuildableExp(Code.ACCOUNT_LOCKED.getCode(), null).variable(time));
            }
            if (matches) {
                // 账号密码匹配
                if (faultMessageExist) {
                    retryCache.remove(username);
                }
            } else {
                //存储失败次数
                int num = faultMessage.num.incrementAndGet();
                faultMessage.lastTime = new Date().getTime();
                getRetryCache().put(username, faultMessage);
                if (num == retryCount) {
                    //账号已经被禁用啦
                    long time = (faultMessage.lastTime + lockTime - new Date().getTime()) / 1000 / 60;
                    throw new AuthenticationException(new BuildableExp(Code.ACCOUNT_LOCKED.getCode(), null).variable(time));
                } else {
                    throw new AuthenticationException(new BuildableExp(Code.ACCOUNT_RETRY_LIMIT.getCode(), null).variable((retryCount - num)));
                }

            }
        }
        if (matches) {
            //保存活跃用户
            String userId = ((ShiroPrincipal) info.getPrincipals().getPrimaryPrincipal()).getUser().getId() + "";
            List<Serializable> caches = getOnLineUserCache().get(userId);
            if (caches == null) {
                caches = new ArrayList<>();
            }
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession();
            Serializable sessionId = session.getId();
            // 如果队列里没有此sessionId,放入队列
            if (!caches.contains(sessionId) && session.getAttribute(EXCEPTION_KEY) == null) {
                caches.add(sessionId);
            }
            getOnLineUserCache().put(userId, caches);
        }
        return matches;
    }


    public void setLockTime(long lockTime) {
        this.lockTime = lockTime;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void setRetryCacheName(String retryCacheName) {
        this.retryCacheName = retryCacheName;
    }

    public void setOnlineUserCacheName(String onlineUserCacheName) {
        this.onlineUserCacheName = onlineUserCacheName;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }
}

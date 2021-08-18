package skrivet.springframework.plugins.security.shiro;

import com.skrivet.components.shiro.filter.ShiroFilterFactoryBean;
import com.skrivet.components.shiro.properties.ShiroProperties;
import com.skrivet.components.shiro.session.SessionDao;
import com.skrivet.components.shiro.session.SessionIdCookie;
import com.skrivet.core.common.convert.CodeConvert;
import com.skrivet.core.common.serializer.SerializerType;
import com.skrivet.plugins.cache.core.CacheConfig;
import com.skrivet.plugins.cache.core.CacheSerializerInitialConfigurations;
import com.skrivet.plugins.queue.core.Channels;
import com.skrivet.plugins.queue.core.subscriber.Subscriber;
import com.skrivet.plugins.security.core.service.PermissionSetService;
import com.skrivet.plugins.security.core.service.ResourceService;
import com.skrivet.plugins.security.core.service.SecurityService;
import com.skrivet.plugins.security.core.service.UserService;
import com.skrivet.plugins.security.shiro.aop.ShiroSecurityAdvisor;
import com.skrivet.plugins.security.shiro.cache.CacheManager;
import com.skrivet.plugins.security.shiro.filter.ConcurrentControlFilter;
import com.skrivet.plugins.security.shiro.filter.DatabaseResourceFilter;
import com.skrivet.plugins.security.shiro.matcher.ShiroLimitRetryCountMatcher;
import com.skrivet.plugins.security.shiro.mgt.WebSecurityManager;
import com.skrivet.plugins.security.shiro.realm.ShiroRealm;
import com.skrivet.plugins.security.shiro.service.ShiroSecurityService;
import com.skrivet.plugins.security.shiro.session.SessionListener;
import com.skrivet.plugins.security.shiro.session.SimpleSessionFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import skrivet.springframework.plugins.security.core.SecurityConfig;

import javax.servlet.Filter;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@AutoConfigureBefore(SecurityConfig.class)
@Configuration
@EnableConfigurationProperties(ShiroProperties.class)
@ConditionalOnProperty(name = "skrivet.security.type", havingValue = "shiro", matchIfMissing = true)
public class ShiroConfig {
    private static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);
    @Autowired
    @Lazy
    private UserService userService;
    @Autowired(required = false)
    private org.springframework.cache.CacheManager cacheManager;
    @Autowired(required = false)
    private CodeConvert codeConvert;
    @Autowired
    @Lazy
    private ResourceService resourceService;
    @Autowired(required = false)
    private Subscriber subscriber;
    @Autowired
    @Lazy
    private PermissionSetService permissionSetService;
    @Value("${skrivet.encryption.name:SHA-1}")
    private String algorithmName;
    @Value("${skrivet.encryption.hashIterations:1024}")
    private int hashIterations;

    /**
     * 基于注解的权限拦截器
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(name = "skrivet.security.webCheck", havingValue = "true", matchIfMissing = true)
    public ShiroSecurityAdvisor shiroSecurityAdvisor() {
        ShiroSecurityAdvisor serviceSecurityAdvisor = new ShiroSecurityAdvisor();
        logger.info("start config controller security interceptor...");
        return serviceSecurityAdvisor;
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Bean
    public SecurityService securityService(ShiroProperties shiroProperties, CacheManager shiroCacheManager, SessionDAO sessionDAO) {
        ShiroSecurityService shiroSecurityService = new ShiroSecurityService();
        shiroSecurityService.setCacheManager(shiroCacheManager);
        shiroSecurityService.setTokenCacheName(shiroProperties.getCache().getTokenCacheName());
        shiroSecurityService.setPermissionInfoCacheName(shiroProperties.getCache().getPermissionInfoCacheName());
        shiroSecurityService.setOnlineUserCacheName(shiroProperties.getCache().getOnlineUserCacheName());
        shiroSecurityService.setSessionDAO(sessionDAO);
        return shiroSecurityService;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    @Bean
    public Cookie sessionIdCookie(ShiroProperties shiroProperties) {
        SimpleCookie simpleCookie = new SessionIdCookie();
        simpleCookie.setMaxAge(shiroProperties.getCookie().getMaxAge());
        simpleCookie.setName(shiroProperties.getCookie().getName());
        return simpleCookie;
    }

    @Bean
    @ConditionalOnProperty(value = "skrivet.cache.type", havingValue = "redis")
    public CacheSerializerInitialConfigurations cacheSerializerInitialConfigurations(ShiroProperties shiroProperties) {
        return () -> {
            //告诉缓存管理器，这三者需要强制使用java的序列化方式
            Map<String, CacheConfig> map = new HashMap<String, CacheConfig>();
            map.put(shiroProperties.getCache().getTokenCacheName(), new CacheConfig(SerializerType.JAVA, null));
            map.put(shiroProperties.getCache().getPermissionInfoCacheName(), new CacheConfig(SerializerType.JAVA, null));
            //map.put(shiroProperties.getSession().getCacheName(), new CacheConfig(SerializerType.JAVA, null));
            //可以使用如下方法，通知其按照配置时间过期
            map.put(shiroProperties.getSession().getCacheName(), new CacheConfig(SerializerType.JAVA, shiroProperties.getSession().getTimeout() > 0 ? Duration.ofMillis(shiroProperties.getSession().getTimeout()) : null));
            map.put(shiroProperties.getCache().getOnlineUserCacheName(), new CacheConfig(SerializerType.JAVA, null));
            map.put(shiroProperties.getCache().getRetryCacheName(), new CacheConfig(SerializerType.JAVA, null));
            return map;
        };
    }

    @Order
    @ConditionalOnMissingBean(CacheManager.class)
    @Bean
    public CacheManager shiroCacheManager() {
        if (cacheManager == null) {
            logger.warn("when you use shiro ,you must config cache !");
            throw new IllegalArgumentException("when you use shiro ,you must config cache !");
        }
        CacheManager shiroCacheManager = new CacheManager(cacheManager);
        return shiroCacheManager;
    }

    @Order
    @ConditionalOnMissingBean(SessionDAO.class)
    @Bean
    public SessionDAO sessionDao(ShiroProperties shiroProperties, CacheManager shiroCacheManager) {
        SessionDao sessionDao = new SessionDao();
        sessionDao.setCacheManager(shiroCacheManager);
        sessionDao.setActiveSessionsCacheName(shiroProperties.getSession().getCacheName());
        return sessionDao;
    }

    @Order
    @ConditionalOnMissingBean(SessionManager.class)
    @Bean
    public SessionManager sessionManager(ShiroProperties shiroProperties, CacheManager shiroCacheManager, Cookie sessionIdCookie, SessionDAO sessionDao) {
        com.skrivet.plugins.security.shiro.session.SessionManager sessionManager = new com.skrivet.plugins.security.shiro.session.SessionManager();
        sessionManager.setSessionDAO(sessionDao);
        sessionManager.setSessionIdCookieEnabled(shiroProperties.getCookie()==null?false:shiroProperties.getCookie().isEnable());
        //不进行会话调度，比较慢
        sessionManager.setSessionValidationSchedulerEnabled(false);
        sessionManager.setSessionIdCookie(sessionIdCookie);
        sessionManager.setCacheManager(shiroCacheManager);
        sessionManager.setGlobalSessionTimeout(shiroProperties.getSession().getTimeout());
        sessionManager.setDeleteInvalidSessions(shiroProperties.getSession().isDeleteInvalid());
        sessionManager.setSessionFactory(new SimpleSessionFactory());
        SessionListener sessionListener = new SessionListener(shiroCacheManager, shiroProperties.getCache().getOnlineUserCacheName());
        sessionManager.getSessionListeners().add(sessionListener);
        return sessionManager;
    }

    @Order
    @ConditionalOnMissingBean(CredentialsMatcher.class)
    @Bean
    public CredentialsMatcher credentialsMatcher(ShiroProperties shiroProperties, CacheManager shiroCacheManager) {
        ShiroLimitRetryCountMatcher matcher = new ShiroLimitRetryCountMatcher();
        matcher.setHashAlgorithmName(algorithmName);
        matcher.setHashIterations(hashIterations);
        matcher.setRetryCacheName(shiroProperties.getCache().getRetryCacheName());
        matcher.setCacheManager(shiroCacheManager);
        matcher.setRetryCount(shiroProperties.getAccountProperties().getPasswordRetryCount());
        matcher.setLockTime(shiroProperties.getAccountProperties().getAccountLockTime());
        matcher.setOnlineUserCacheName(shiroProperties.getCache().getOnlineUserCacheName());
        return matcher;
    }

    @Order
    @ConditionalOnMissingBean(Realm.class)
    @Bean
    public Realm realm(ShiroProperties shiroProperties, CacheManager shiroCacheManager, SecurityService securityService, CredentialsMatcher credentialsMatcher) {
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setUserService(userService);
        shiroRealm.setPermissionSetService(permissionSetService);
        shiroRealm.setCachingEnabled(true);
        //设置对用户凭证的缓存
        shiroRealm.setAuthenticationCachingEnabled(true);
        //设置对用户信息的缓存
        shiroRealm.setAuthorizationCachingEnabled(true);
        shiroRealm.setAuthenticationCacheName(shiroProperties.getCache().getTokenCacheName());
        shiroRealm.setAuthorizationCacheName(shiroProperties.getCache().getPermissionInfoCacheName());
        shiroRealm.setCacheManager(shiroCacheManager);
        shiroRealm.setCredentialsMatcher(credentialsMatcher);
        return shiroRealm;
    }

    @Order
    @ConditionalOnMissingBean(SecurityManager.class)
    @Bean
    public SecurityManager securityManager(Realm realm, SessionManager sessionManager, CacheManager shiroCacheManager, ShiroProperties shiroProperties) {
        DefaultWebSecurityManager securityManager = new WebSecurityManager(shiroCacheManager, shiroProperties.getCache().getOnlineUserCacheName());
        securityManager.setRealm(realm);
        securityManager.setSessionManager(sessionManager);
        securityManager.setRememberMeManager(null);
        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }

    @Bean
    public ConcurrentControlFilter concurrentControlFilter(SecurityService securityService, CacheManager shiroCacheManager, ShiroProperties shiroProperties) {
        ConcurrentControlFilter concurrentControlFilter = new ConcurrentControlFilter();
        concurrentControlFilter.setCacheManager(shiroCacheManager);
        concurrentControlFilter.setOnlineUserCacheName(shiroProperties.getCache().getOnlineUserCacheName());
        concurrentControlFilter.setSecurityService(securityService);
        concurrentControlFilter.setSessionCacheName(shiroProperties.getSession().getCacheName());
        concurrentControlFilter.setMaxSession(shiroProperties.getAccountProperties().getMaxAccount());
        return concurrentControlFilter;
    }

    @Bean
    public DatabaseResourceFilter databaseResourceFilter() {
        DatabaseResourceFilter databaseResourceFilter = new DatabaseResourceFilter();
        databaseResourceFilter.setResourceService(resourceService);
        if (subscriber != null) {
            subscriber.registerSubscribe(Channels.RELOAD_RESOURCE, (channel, dat) -> {
                databaseResourceFilter.loadDataBasePermissions();
            });
        }

        return databaseResourceFilter;
    }

    @Order
    @ConditionalOnMissingBean(org.apache.shiro.spring.web.ShiroFilterFactoryBean.class)
    @Bean
    public org.apache.shiro.spring.web.ShiroFilterFactoryBean shiroFilterFactoryBean(DatabaseResourceFilter databaseResourceFilter, ConcurrentControlFilter concurrentControlFilter, ShiroProperties shiroProperties, SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, Filter> filters = new HashMap<>(2);
        filters.put("concurrentControlFilter", concurrentControlFilter);
        filters.put("databaseResourceFilter", databaseResourceFilter);
        shiroFilterFactoryBean.setFilters(filters);
        Map<String, String> filterChainDefinitionMap = new HashMap<>(2);
        for (String str : shiroProperties.getLoginUrl().split(",")) {
            filterChainDefinitionMap.put(str, "concurrentControlFilter");
        }
        filterChainDefinitionMap.put("/**", "databaseResourceFilter");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }
}

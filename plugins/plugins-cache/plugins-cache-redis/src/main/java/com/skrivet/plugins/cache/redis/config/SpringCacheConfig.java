package com.skrivet.plugins.cache.redis.config;


import com.skrivet.components.redis.config.RedisConfig;
import com.skrivet.core.common.aop.Order;
import com.skrivet.core.common.serializer.SerializerType;
import com.skrivet.plugins.cache.core.CacheConfig;
import com.skrivet.plugins.cache.core.CacheSerializerInitialConfigurations;
import com.skrivet.plugins.cache.core.provider.CacheProvider;
import com.skrivet.plugins.cache.redis.plus.RedisCacheManagerPlus;
import com.skrivet.plugins.cache.redis.plus.RedisCacheWriterPlus;
import com.skrivet.plugins.cache.redis.provider.RedisCacheProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.HashMap;
import java.util.Map;


/**
 * spring 缓存配置<br/>
 * 当skrivet.cache.type=redis时，表示使用redis作为spring的缓存
 *
 * @author n
 * @version 1.0
 */
@Configuration
@ConditionalOnProperty(value = "skrivet.cache.type", havingValue = "redis")
@EnableCaching(proxyTargetClass = true, order = Order.CACHE_SERVICE)
public class SpringCacheConfig extends CachingConfigurerSupport {
    private static final Logger logger = LoggerFactory.getLogger(SpringCacheConfig.class);

    @Value("${skrivet.project.name}")
    private String name;
    @Value("${skrivet.cache.value-serialization:'json'}")
    private SerializerType serializerType;
    @Autowired(required = false)
    private RedisConnectionFactory connectionFactory;
    @Autowired(required = false)
    private CacheSerializerInitialConfigurations cacheSerializerInitialConfigurations;

    /**
     * spring的缓存管理器
     * @return
     */
    @Bean
    public CacheManager cacheManager() {
        logger.debug("start config cache manager,this cache's prefix is {}", name);
        if (connectionFactory == null) {
            logger.warn("can not config spring cache manager , redis is not configured...");
            return null;
        }
        RedisCacheConfiguration defaultCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        //设置名称前缀，多个项目可以进行隔离
        defaultCacheConfiguration = defaultCacheConfiguration.computePrefixWith(cacheName -> name + "::" + cacheName + "::");
        //设置值序列化方式
        switch (serializerType) {
            case JSON:
                logger.info("the cache key will use json serialize...");
                defaultCacheConfiguration = defaultCacheConfiguration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisConfig.json()));
                break;
            case JAVA:
                logger.info("the cache key will use jdk serialize...");
                defaultCacheConfiguration = defaultCacheConfiguration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.java(null)));
                break;
            default:
                throw new IllegalArgumentException("not support this serialize type!");
        }
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        if (cacheSerializerInitialConfigurations != null) {
            //缓存配置，每个缓存可以由单独的过期时间、序列化方式
            Map<String, CacheConfig> map = cacheSerializerInitialConfigurations.initialCacheSerializerConfigurations();
            for (String key : map.keySet()) {
                SerializerType serializerType = map.get(key).getSerializerType();
                RedisCacheConfiguration redisCacheConfiguration = null;
                switch (serializerType) {
                    case JSON:
                        redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                                .computePrefixWith(cacheName -> name + "::" + cacheName + "::")
                                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisConfig.json()));
                        break;
                    case JAVA:
                        redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                                .computePrefixWith(cacheName -> name + "::" + cacheName + "::")
                                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.java()));

                        break;
                    default:
                        throw new IllegalArgumentException("not support this serialize type!");
                }
                if (map.get(key).getDuration() != null) {
                    redisCacheConfiguration = redisCacheConfiguration.entryTtl(map.get(key).getDuration());
                }
                cacheConfigurations.put(key, redisCacheConfiguration);
            }
        }
        RedisCacheManagerPlus redisCacheManagerPlus = new RedisCacheManagerPlus(new RedisCacheWriterPlus(connectionFactory), defaultCacheConfiguration, cacheConfigurations,
                true);
        redisCacheManagerPlus.setTransactionAware(true);
//        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory);
//        builder.transactionAware().cacheDefaults(defaultCacheConfiguration);
//        if (cacheConfigurations.size() > 0) {
//            builder.withInitialCacheConfigurations(cacheConfigurations);
//        }
//        RedisCacheManager redisCacheManager = builder.build();
        return redisCacheManagerPlus;
    }


    /**
     * 缓存提供者，以供其他的代码使用
     *
     * @param redisTemplate redis操作模板
     * @return 缓存提供者
     */
    @Bean
    public CacheProvider cacheProvider(RedisTemplate redisTemplate) {
        RedisCacheProvider redisCacheProvider = new RedisCacheProvider(name, redisTemplate);
        return redisCacheProvider;
    }
}

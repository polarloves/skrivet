package com.skrivet.components.redis.config;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skrivet.components.redis.properties.RedisProperties;
import com.skrivet.core.common.serializer.SerializerType;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(RedisOperations.class)
@EnableConfigurationProperties(RedisProperties.class)
@Import({LettuceConnectionConfiguration.class})
@ImportAutoConfiguration(exclude = {RedisAutoConfiguration.class})
public class RedisConfig {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory, RedisProperties redisProperties) {
        RedisTemplate template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(serializer(redisProperties.getKeySerialization()));
        template.setValueSerializer(serializer(redisProperties.getValueSerialization()));
        template.setHashKeySerializer(serializer(redisProperties.getHashKeySerialization()));
        template.setHashValueSerializer(serializer(redisProperties.getHashValueSerialization()));
        template.afterPropertiesSet();
        return template;
    }


    private RedisSerializer<?> serializer(SerializerType type) {
        switch (type) {
            case JAVA:
                return new JdkSerializationRedisSerializer();
            case JSON:
                return json();
            case STRING:
                return new StringRedisSerializer();
        }
        return new GenericJackson2JsonRedisSerializer();
    }
    public  static RedisSerializer<Object> json() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        objectMapper.setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        GenericJackson2JsonRedisSerializer result = new GenericJackson2JsonRedisSerializer();
        return result;
    }

}

package skrivet.springframework.plugins.security.jwt;

import com.skrivet.components.jwt.properties.JWTProperties;
import com.skrivet.plugins.queue.core.Channels;
import com.skrivet.plugins.queue.core.subscriber.Subscriber;
import com.skrivet.plugins.security.core.encryption.EncryptionService;
import com.skrivet.plugins.security.core.service.PermissionSetService;
import com.skrivet.plugins.security.core.service.ResourceService;
import com.skrivet.plugins.security.core.service.SecurityService;
import com.skrivet.plugins.security.core.service.UserService;
import com.skrivet.plugins.security.jwt.aop.JWTSecurityAdvisor;
import com.skrivet.plugins.security.jwt.filter.JWTFilterFactoryBean;
import com.skrivet.plugins.security.jwt.service.JwtSecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import skrivet.springframework.plugins.security.core.SecurityConfig;

@AutoConfigureBefore(SecurityConfig.class)
@Configuration
@EnableConfigurationProperties(JWTProperties.class)
@ConditionalOnProperty(name = "skrivet.security.type", havingValue = "jwt")
public class JWTConfig {
    @Autowired(required = false)
    private Subscriber subscriber;
    private static final Logger logger = LoggerFactory.getLogger(JWTConfig.class);

    @Bean
    public JWTFilterFactoryBean jwtFilterFactoryBean(JWTProperties jwtProperties, @Lazy ResourceService resourceService,@Lazy UserService userService,@Lazy PermissionSetService permissionSetService) {
        JWTFilterFactoryBean jwtFilterFactoryBean = new JWTFilterFactoryBean(resourceService, userService,permissionSetService,jwtProperties.getTokenKey(), jwtProperties.getActionKey(), jwtProperties.getSignKey(), jwtProperties.getTimeout(), jwtProperties.getRefreshBeforeTimeout());
        if (subscriber != null) {
            subscriber.registerSubscribe(Channels.RELOAD_RESOURCE, (channel, dat) -> {
                jwtFilterFactoryBean.loadDataBasePermissions();
            });
        }
        return jwtFilterFactoryBean;
    }

    @Bean
    @ConditionalOnProperty(name = "skrivet.security.webCheck", havingValue = "true", matchIfMissing = true)
    public JWTSecurityAdvisor jwtSecurityAdvisor() {
        JWTSecurityAdvisor serviceSecurityAdvisor = new JWTSecurityAdvisor();
        logger.info("config permission filter,this will work on controller...");
        return serviceSecurityAdvisor;
    }

    @Bean
    public SecurityService jwtSecurityService(JWTProperties jwtProperties, @Lazy UserService userService, @Lazy PermissionSetService permissionSetService, EncryptionService encryptionService) {
        SecurityService jwtSecurityService = new JwtSecurityService(userService, permissionSetService, encryptionService, jwtProperties.getSignKey(), jwtProperties.getTimeout());
        return jwtSecurityService;
    }
}

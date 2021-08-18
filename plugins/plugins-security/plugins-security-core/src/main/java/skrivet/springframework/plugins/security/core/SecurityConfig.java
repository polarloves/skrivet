package skrivet.springframework.plugins.security.core;

import com.skrivet.plugins.security.core.encryption.EncryptionService;
import com.skrivet.plugins.security.core.encryption.HashEncryptionService;
import com.skrivet.plugins.security.core.service.ResourceService;
import com.skrivet.plugins.security.core.service.SecurityService;
import com.skrivet.plugins.security.core.service.impl.EmptySecurityService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.List;
import java.util.Map;

/**
 * 加密配置
 */

@Configuration
public class SecurityConfig {
    @Value("${skrivet.encryption.name:SHA-1}")
    private String algorithmName;
    @Value("${skrivet.encryption.hashIterations:1024}")
    private int hashIterations;

    @Order
    @Bean
    @ConditionalOnMissingBean(SecurityService.class)
    public SecurityService emptySecurityService() {
        return new EmptySecurityService();
    }

    @Bean
    public EncryptionService encryptionService() {
        //加密器，用于对密码加密
        EncryptionService encryptionService = new HashEncryptionService(algorithmName, hashIterations);
        return encryptionService;
    }
    @Order
    @ConditionalOnMissingBean(ResourceService.class)
    @Bean
    public ResourceService emptyResourceService() {
        return new ResourceService() {
            @Override
            public Map<String, List<String>> resourcePermissions() {
                return null;
            }
        };
    }
}

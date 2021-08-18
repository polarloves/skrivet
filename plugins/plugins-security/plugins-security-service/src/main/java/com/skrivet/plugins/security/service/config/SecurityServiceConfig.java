package com.skrivet.plugins.security.service.config;

import com.skrivet.plugins.security.service.aop.ServiceSecurityAdvisor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.skrivet.plugins.security.core.service.UserService;

@ConditionalOnProperty(name = "skrivet.security.serviceCheck", havingValue = "true")
@Configuration
@ConditionalOnBean(UserService.class)
public class SecurityServiceConfig {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public ServiceSecurityAdvisor serviceSecurityAdvisor() {
        ServiceSecurityAdvisor serviceSecurityAdvisor = new ServiceSecurityAdvisor();
        logger.info("start config service security intercept...");
        return serviceSecurityAdvisor;
    }
}

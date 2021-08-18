package com.skrivet.plugins.service.core.config;

import com.skrivet.plugins.service.core.aop.MethodValidationPostProcessor;
import com.skrivet.plugins.service.core.aop.ServiceExceptionAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;

import javax.validation.Validator;

@Configuration
public class ServiceConfig {
    @Bean
    public ServiceExceptionAdvisor serviceExceptionAdvisor() {
        return new ServiceExceptionAdvisor();
    }

    @ConditionalOnProperty(name = "skrivet.validate.service", havingValue = "true")
    @Bean
    public static MethodValidationPostProcessor methodValidationPostProcessor(Environment environment,
                                                                              @Lazy Validator validator) {
        MethodValidationPostProcessor processor = new MethodValidationPostProcessor();
        boolean proxyTargetClass = environment.getProperty("spring.aop.proxy-target-class", Boolean.class, true);
        processor.setProxyTargetClass(proxyTargetClass);
        processor.setValidator(validator);
        return processor;
    }
}

package com.skrivet.plugins.database.core.config;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * 不使用spring-boot的自动化配置~
 */
@ImportAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@Configuration
public class DatabaseConfig {
}

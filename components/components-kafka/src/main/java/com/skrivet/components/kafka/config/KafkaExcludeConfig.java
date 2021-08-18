package com.skrivet.components.kafka.config;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@ImportAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration.class})
public class KafkaExcludeConfig {
}

package com.skrivet.plugins.database.mongodb.config;

import com.skrivet.plugins.database.mongodb.annotations.EnableMultiMongo;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.Collections;

@ImportAutoConfiguration(exclude = {MongoAutoConfiguration.class})
@Configuration
@EnableMultiMongo
public class MongoConfig {
    @Bean
    MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(Collections.emptyList());
    }
}

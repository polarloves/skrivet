package com.skrivet.supports.exception.dao.mongodb;

import com.skrivet.plugins.database.mongodb.annotations.MongoScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MongoScan(basePackages = "com.skrivet.supports.exception.dao.mongodb",db = "exception")
public class ExceptionLogConfig {
}
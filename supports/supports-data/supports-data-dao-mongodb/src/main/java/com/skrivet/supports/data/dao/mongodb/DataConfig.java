package com.skrivet.supports.data.dao.mongodb;

import com.skrivet.plugins.database.mongodb.annotations.MongoScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MongoScan(basePackages = "com.skrivet.supports.data.dao.mongodb", db = "data")
public class DataConfig {
}

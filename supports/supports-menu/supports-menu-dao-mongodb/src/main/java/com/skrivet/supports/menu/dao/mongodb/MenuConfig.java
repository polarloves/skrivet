package com.skrivet.supports.menu.dao.mongodb;

import com.skrivet.plugins.database.mongodb.annotations.MongoScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MongoScan(basePackages = "com.skrivet.supports.menu.dao.mongodb")
public class MenuConfig {
}
package com.skrivet.supports.authority.dao.mongodb;

import com.skrivet.plugins.database.mongodb.annotations.MongoScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MongoScan(basePackages = "com.skrivet.supports.authority.dao.mongodb", db = "authority")
public class AuthorityConfig {
}

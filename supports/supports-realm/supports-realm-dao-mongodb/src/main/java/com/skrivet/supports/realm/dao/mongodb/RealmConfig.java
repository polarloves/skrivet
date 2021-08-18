package com.skrivet.supports.realm.dao.mongodb;

import com.skrivet.plugins.database.mongodb.annotations.MongoScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MongoScan(basePackages = "com.skrivet.supports.realm.dao.mongodb", db = "realm")
public class RealmConfig {
}

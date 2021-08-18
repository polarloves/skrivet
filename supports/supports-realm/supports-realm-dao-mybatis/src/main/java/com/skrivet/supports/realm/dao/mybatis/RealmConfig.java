package com.skrivet.supports.realm.dao.mybatis;

import com.skrivet.plugins.database.mybatis.annotations.DaoScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@DaoScan(basePackages = "com.skrivet.supports.realm.dao.mybatis", db = "realm", location = "classpath*:mapping/supports/realm/**.xml")
public class RealmConfig {
}

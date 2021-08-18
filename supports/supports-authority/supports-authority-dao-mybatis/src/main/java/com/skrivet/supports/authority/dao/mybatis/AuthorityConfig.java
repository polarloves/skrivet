package com.skrivet.supports.authority.dao.mybatis;

import com.skrivet.plugins.database.mybatis.annotations.DaoScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@DaoScan(basePackages = "com.skrivet.supports.authority.dao.mybatis", db = "authority", location = "classpath*:mapping/supports/authority/**.xml")
public class AuthorityConfig {
}

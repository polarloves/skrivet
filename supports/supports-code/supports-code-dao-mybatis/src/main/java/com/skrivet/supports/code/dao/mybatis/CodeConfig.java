package com.skrivet.supports.code.dao.mybatis;

import com.skrivet.plugins.database.mybatis.annotations.DaoScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@DaoScan(basePackages = "com.skrivet.supports.code.dao.mybatis", db = "code", location = "classpath*:mapping/supports/code/**.xml")
public class CodeConfig {
}

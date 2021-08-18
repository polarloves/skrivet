package com.skrivet.supports.data.dao.mybatis;

import com.skrivet.plugins.database.mybatis.annotations.DaoScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@DaoScan(basePackages = "com.skrivet.supports.data.dao.mybatis", db = "data", location = "classpath*:mapping/supports/data/**.xml")
public class DataConfig {
}
